package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.common.SpecificationUtils;
import com.mine.manager.common.enums.StateLoadEnum;
import com.mine.manager.exception.DuplicateException;
import com.mine.manager.exception.EntityNotFoundException;
import com.mine.manager.exception.InvalidValueException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.LoadRepository;
import com.mine.manager.parameters.data.repository.PercentageLiquidationRepository;
import com.mine.manager.parameters.domain.entity.Load;
import com.mine.manager.parameters.domain.entity.PercentageLiquidation;
import com.mine.manager.parameters.domain.mapper.PercentageLiquidationMapper;
import com.mine.manager.parameters.domain.service.Interfaces.*;
import com.mine.manager.parameters.presentation.request.dto.LiquidPayableCalculationDto;
import com.mine.manager.parameters.presentation.request.dto.PercentageLiquidationDto;
import com.mine.manager.parameters.presentation.request.filter.LiquidationFilter;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import com.mine.manager.parameters.presentation.response.pojo.PercentageLiquidationPojo;
import com.mine.manager.util.FieldsFilterUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class PercentageLiquidationServiceImpl extends CRUDServiceImpl<PercentageLiquidation, Integer> implements
        PercentageLiquidationService {


    private final PercentageLiquidationRepository percentageLiquidationRepository;
    private final PercentageLiquidationMapper percentageLiquidationMapper;
    private final SupplierService supplierService;
    private final LoadService loadService;
    private final LoadRepository loadRepository;
    private final AdvanceService advanceService;
    private final DataSource dataSource;


    private static final String LIQUIDATION = SpanishEntityNameProvider.getSpanishName(PercentageLiquidation.class.getSimpleName());

    private static final BigDecimal ONZA_TROY = new BigDecimal("31.1035");
    private static final BigDecimal CIEN = new BigDecimal("100");
    private static final BigDecimal MIL = new BigDecimal("1000");


    @Override
    public List<PercentageLiquidationPojo> getPercentageLiquidations() {
        List<PercentageLiquidation> list = percentageLiquidationRepository.findAllByActiveIsTrue();
        return percentageLiquidationMapper.toPojoList(list);
    }


    @Override
    public PercentageLiquidationPojo getPercentageLiquidationById(Integer id) {
        PercentageLiquidation percentageLiquidation = percentageLiquidationRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException(LIQUIDATION, id.toString()));
        return percentageLiquidationMapper.toPojo(percentageLiquidation);
    }


    @Override
    @Transactional
    public PercentageLiquidationPojo create(PercentageLiquidationDto dto) {
        PercentageLiquidation percentageLiquidation = new PercentageLiquidation();
        percentageLiquidationMapper.fromDto(dto, percentageLiquidation);
        this.updateEntities(dto, percentageLiquidation, false);
        calculateTotalPrice(percentageLiquidation);
        PercentageLiquidation savedPercentageLiquidation = percentageLiquidationRepository.save(percentageLiquidation);
        return percentageLiquidationMapper.toPojo(savedPercentageLiquidation);
    }


    private void calculateTotalPrice(PercentageLiquidation percentageLiquidation) {

        LiquidPayableCalculationDto liquidPayableCalculationDto =
                LiquidPayableCalculationDto.builder().quotation(percentageLiquidation.getQuotation())
                        .dmSilver(percentageLiquidation.getDmSilver())
                        .dryMetricKilograms(percentageLiquidation.getDryMetricKilograms())
                        .percentage(percentageLiquidation.getPercentage())
                        .exchangeRate(percentageLiquidation.getExchangeRate()).build();


        BigDecimal totalPrice = calculatePayableAg(liquidPayableCalculationDto);

        percentageLiquidation.setTotalPrice(totalPrice);


        BigDecimal cajaNacionalAmount =
                calculateDiscount(totalPrice, percentageLiquidation.getCajaNacional());


        BigDecimal fedecominAmount =
                calculateDiscount(totalPrice, percentageLiquidation.getFedecomin());


        BigDecimal fencominAmount =
                calculateDiscount(totalPrice, percentageLiquidation.getFencomin());


        BigDecimal comibolAmount =
                calculateDiscount(totalPrice, percentageLiquidation.getComibol());


        BigDecimal transportation =
                calculateDiscount(totalPrice, percentageLiquidation.getTransportation());


        BigDecimal miningRoyaltiesAmount =
                calculateDiscount(totalPrice, percentageLiquidation.getMiningRoyalties());


        BigDecimal firstAdvance = advanceService.getTotalAdvancesByLoad(percentageLiquidation.getLoad().getId()).getTotalAmount();
        percentageLiquidation.setFirstAdvance(firstAdvance);


        BigDecimal totalDiscounts = cajaNacionalAmount
                .add(fedecominAmount)
                .add(fencominAmount)
                .add(comibolAmount)
                .add(transportation)
                .add(miningRoyaltiesAmount)
                .add(firstAdvance)
                .setScale(2, RoundingMode.HALF_UP);


        percentageLiquidation.setDiscounts(totalDiscounts);


        BigDecimal netPrice = totalPrice
                .subtract(totalDiscounts)
                .setScale(2, RoundingMode.HALF_UP);


        BigDecimal amountPayableTc = netPrice
                .divide(percentageLiquidation.getExchangeRate(), 2, RoundingMode.HALF_UP);


        percentageLiquidation.setAmountPayableTc(amountPayableTc);


        percentageLiquidation.setAmountPayableBc(netPrice);
    }

    @Override
    public BigDecimal calculatePayableAg(LiquidPayableCalculationDto dto) {

        // 1. (dmSilver * 100 / 31.1035) -> Valor de la ley convertida
        BigDecimal leyConvertida = dto.getDmSilver()
                .multiply(CIEN)
                .divide(ONZA_TROY, 10, RoundingMode.HALF_UP);

        // 2. Multiplicar por Cotización y Porcentaje (dividido entre 100)
        BigDecimal porcentajeDecimal = dto.getPercentage()
                .divide(CIEN, 10, RoundingMode.HALF_UP);

        BigDecimal valorBrutoAg = leyConvertida
                .multiply(dto.getQuotation())
                .multiply(porcentajeDecimal);

        // 3. Ajuste por mil y aplicar el Tipo de Cambio
        BigDecimal valorPorKilo = valorBrutoAg
                .divide(MIL, 10, RoundingMode.HALF_UP)
                .multiply(dto.getExchangeRate());

        // 4. Resultado Final: Multiplicar por el Peso Métrico Seco
        return valorPorKilo.multiply(dto.getDryMetricKilograms())
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDiscount(BigDecimal totalPrice, BigDecimal percentage) {
        if (percentage == null) {
            return BigDecimal.ZERO;
        }


        return totalPrice
                .multiply(percentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }


    private void updateEntities(PercentageLiquidationDto dto, PercentageLiquidation percentageLiquidation, boolean isUpdate) {


        Load load = loadService.getById(dto.getLoadId());


        if (!isUpdate) {
            if (!load.getState().equals(StateLoadEnum.PENDING)) {
                throw new InvalidValueException("Solo se puede crear una liquidación de una carga en estado pendiente");
            }
            if (percentageLiquidationRepository.existsByLoadIdAndActiveTrue(dto.getLoadId())) {
                throw new DuplicateException(LIQUIDATION, "Carga", load.getCorrelativeLotCode());
            }
            load.setState(StateLoadEnum.LIQUIDATED);
        } else {
            if (percentageLiquidationRepository.existsByLoadIdAndIdNotAndActiveTrue(dto.getLoadId(), percentageLiquidation.getId())) {
                throw new DuplicateException(LIQUIDATION, "Carga", load.getCorrelativeLotCode());
            }
        }


        if (dto.getSupplierId() != null) {
            load.setSupplier(supplierService.getById(dto.getSupplierId()));
        }

        if (dto.getMetricWetKilograms() != null) {
            load.setWeight(dto.getMetricWetKilograms());
        }

        loadRepository.save(load);
        percentageLiquidation.setLoad(load);
    }


    @Override
    @Transactional
    public PercentageLiquidationPojo update(Integer id, PercentageLiquidationDto dto) {
        PercentageLiquidation percentageLiquidation = this.getById(id);
        percentageLiquidationMapper.fromDto(dto, percentageLiquidation);
        updateEntities(dto, percentageLiquidation, true);
        calculateTotalPrice(percentageLiquidation);
        PercentageLiquidation savedPercentageLiquidation = percentageLiquidationRepository.save(percentageLiquidation);
        return percentageLiquidationMapper.toPojo(savedPercentageLiquidation);
    }


    @Override
    protected GenericRepository<PercentageLiquidation, Integer> getRepository() {
        return percentageLiquidationRepository;
    }

    @Override
    public PagePojo<PercentageLiquidationPojo> getByPageAndFilters(int page, int size, String sortBy,
                                                                   String sortOrder, LiquidationFilter filter) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<PercentageLiquidation> spec = this.generateSpecification(filter);
        Page<PercentageLiquidation> filtered = percentageLiquidationRepository.findAll(spec, pageable);
        return percentageLiquidationMapper.fromPageToPagePojo(filtered);
    }

    @Override
    public byte[] generatePercentageLiquidationPdf(Integer idPercentageLiquidation) {

        PercentageLiquidationPojo liquidation = this.getPercentageLiquidationById(idPercentageLiquidation);

        try {
            InputStream reportStream =
                    getClass().getResourceAsStream("/reports/emcar_percentage_report.jrxml");

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(reportStream);

            Map<String, Object> params = new HashMap<>();
            params.put("idLiquidation", idPercentageLiquidation);

            BigDecimal amount = liquidation.getAmountPayableBc();

            if (amount != null) {
                int parteEntera = amount.intValue();

                int cents = amount.remainder(BigDecimal.ONE)
                        .multiply(new BigDecimal("100"))
                        .intValue();

                String letters = com.mine.manager.util.NumberToLetters.convertToLetters(new BigDecimal(parteEntera));

                String totalLiteral = String.format("SON: %s %02d/100 BOLIVIANOS", letters, cents);

                params.put("total", totalLiteral);
            } else {
                params.put("total", "SON: CERO 00/100 BOLIVIANOS");
            }

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport,
                            params,
                            dataSource.getConnection()
                    );

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de liquidación", e);
        }
    }
/*
    @Override
    public byte[] generatePercentageLiquidationPdf(Integer idPercentageLiquidation) {


        this.getById(idPercentageLiquidation);


        try {
            InputStream reportStream =
                    getClass().getResourceAsStream("/reports/emcar_percentage_report.jrxml");


            JasperReport jasperReport =
                    JasperCompileManager.compileReport(reportStream);


            Map<String, Object> params = new HashMap<>();
            params.put("idLiquidation", idPercentageLiquidation);


            InputStream logoStream =
                    getClass().getResourceAsStream("/reports/logo.png");
            params.put("logo", logoStream);


            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport,
                            params,
                            dataSource.getConnection()
                    );


            return JasperExportManager.exportReportToPdf(jasperPrint);


        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de liquidación", e);
        }
    }
*/

    @Override
    public List<PercentageLiquidationPojo> getFiltered(LiquidationFilter filter) {
        Specification<PercentageLiquidation> spec = this.generateSpecification(filter);
        List<PercentageLiquidation> filtered = percentageLiquidationRepository.findAll(spec);
        return percentageLiquidationMapper.toPojoList(filtered);
    }


    private Specification<PercentageLiquidation> generateSpecification(LiquidationFilter filter) {
        FieldsFilterUtil fields = new FieldsFilterUtil();
        fields.addEqualsField("active", true);
        fields.addLikeField("load.correlativeLotCode", filter.getCorrelativeLotCode());
        fields.addLikeField("load.supplier.name", filter.getSupplierName());
        fields.addDateField("percentageLiquidationDate", filter.getStartDate()
                , filter.getEndDate() != null ? filter.getEndDate() : LocalDate.now());
        if (filter.getSome() != null && !filter.getSome().isBlank()) {
            List<Integer> someIds = percentageLiquidationRepository.findIdsBySome(filter.getSome());
            fields.addInSomeField("id", someIds);
        }
        return SpecificationUtils.createSpecification(fields.getFilterFields());
    }
}

