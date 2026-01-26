package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.common.SpecificationUtils;
import com.mine.manager.common.enums.LiquidationTypeEnum;
import com.mine.manager.common.enums.StateLoadEnum;
import com.mine.manager.exception.DuplicateException;
import com.mine.manager.exception.EntityNotFoundException;
import com.mine.manager.exception.InvalidValueException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.LiquidationRepository;
import com.mine.manager.parameters.data.repository.LoadRepository;
import com.mine.manager.parameters.domain.entity.Liquidation;
import com.mine.manager.parameters.domain.entity.Load;
import com.mine.manager.parameters.domain.mapper.LiquidationMapper;
import com.mine.manager.parameters.domain.service.Interfaces.*;
import com.mine.manager.parameters.presentation.request.dto.LiquidationDto;
import com.mine.manager.parameters.presentation.request.dto.RoyaltyCalculationDto;
import com.mine.manager.parameters.presentation.request.filter.LiquidationFilter;
import com.mine.manager.parameters.presentation.response.pojo.LiquidationPojo;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import com.mine.manager.parameters.presentation.response.pojo.RoyaltyCalculationPojo;
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
public class LiquidationServiceImpl extends CRUDServiceImpl<Liquidation, Integer> implements
        LiquidationService {


    private final LiquidationRepository liquidationRepository;
    private final LiquidationMapper liquidationMapper;
    private final SupplierService supplierService;
    private final MineService mineService;
    private final MineralService mineralService;
    private final TypeMineralService typeMineralService;
    private final CooperativeService cooperativeService;
    private final LoadService loadService;
    private final LoadRepository loadRepository;
    private final AdvanceService advanceService;
    private final DataSource dataSource;


    private static final String LIQUIDATION = SpanishEntityNameProvider.getSpanishName(Liquidation.class.getSimpleName());


    private static final BigDecimal LBS_PER_KG = new BigDecimal("2.2046223");
    private static final BigDecimal GRAMS_PER_TROY_OZ = new BigDecimal("31.1035");
    private static final BigDecimal ALICUOTA_ZINC = new BigDecimal("0.03");
    private static final BigDecimal ALICUOTA_PLATA = new BigDecimal("0.036");
    private static final BigDecimal ALICUOTA_PLOMO = new BigDecimal("0.03");

    @Override
    public List<LiquidationPojo> getLiquidations() {
        List<Liquidation> list = liquidationRepository.findAllByActiveIsTrue();
        return liquidationMapper.toPojoList(list);
    }


    @Override
    public LiquidationPojo getLiquidationById(Integer id) {
        Liquidation liquidation = liquidationRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException(LIQUIDATION, id.toString()));
        return liquidationMapper.toPojo(liquidation);
    }


    @Override
    @Transactional
    public LiquidationPojo create(LiquidationDto dto) {
        Liquidation liquidation = new Liquidation();
        liquidationMapper.fromDto(dto, liquidation);
        if (liquidation.getLiquidationTypeEnum() == LiquidationTypeEnum.COOPERATIVE
                && dto.getCooperativeId() == null) {

            throw new InvalidValueException(
                    "Debe seleccionar una Cooperativa para procesar este tipo de liquidación."
            );
        }
        this.updateEntities(dto, liquidation, false);
        calculateTotalPrice(liquidation);
        Liquidation savedLiquidation = liquidationRepository.save(liquidation);
        return liquidationMapper.toPojo(savedLiquidation);
    }

    private void calculateTotalPrice(Liquidation liquidation) {
        BigDecimal valueSilver = liquidation.getPriceSilver().multiply(liquidation.getLawSilver());
        BigDecimal valueZinc = liquidation.getPriceZinc().multiply(liquidation.getLawZinc());
        BigDecimal valueLead = liquidation.getPriceLead().multiply(liquidation.getLawLead());

        BigDecimal mineralValue = valueSilver.add(valueZinc).add(valueLead);

        BigDecimal tms = liquidation.getDryMetricKilograms()
                .divide(BigDecimal.valueOf(1000), 6, RoundingMode.HALF_UP);

        BigDecimal totalPrice = mineralValue
                .multiply(liquidation.getExchangeRate())
                .multiply(tms)
                .setScale(2, RoundingMode.HALF_UP);

        liquidation.setTotalPrice(totalPrice);

        BigDecimal cajaNacionalAmount =
                calculateDiscount(totalPrice, liquidation.getCajaNacional());

        BigDecimal fedecominAmount =
                calculateDiscount(totalPrice, liquidation.getFedecomin());

        BigDecimal fencominAmount =
                calculateDiscount(totalPrice, liquidation.getFencomin());

        BigDecimal comibolAmount =
                calculateDiscount(totalPrice, liquidation.getComibol());

        BigDecimal cooperativeContributionAmount =
                calculateDiscount(totalPrice, liquidation.getCooperativeContribution());

        BigDecimal miningRoyaltiesAmount =
                calculateDiscount(totalPrice, liquidation.getMiningRoyalties());

        BigDecimal firstAdvance = advanceService.getTotalAdvancesByLoad(liquidation.getLoad().getId()).getTotalAmount();
        liquidation.setFirstAdvance(firstAdvance);

        BigDecimal totalDiscounts = cajaNacionalAmount
                .add(fedecominAmount)
                .add(fencominAmount)
                .add(comibolAmount)
                .add(cooperativeContributionAmount)
                .add(miningRoyaltiesAmount)
                .add(liquidation.getRoyaltyLead())
                .add(liquidation.getRoyaltySilver())
                .add(liquidation.getRoyaltyZinc())
                .add(firstAdvance)
                .add(liquidation.getSecondAdvance())
                .setScale(2, RoundingMode.HALF_UP);

        liquidation.setDiscounts(totalDiscounts);

        BigDecimal netPrice = totalPrice
                .subtract(totalDiscounts)
                .add(liquidation.getTransportationBonus())
                .setScale(2, RoundingMode.HALF_UP);


        BigDecimal amountPayableTc = netPrice
                .divide(liquidation.getExchangeRate(), 2, RoundingMode.HALF_UP);

        liquidation.setAmountPayableTc(amountPayableTc);

        liquidation.setAmountPayableBc(netPrice);
    }

    private BigDecimal calculateDiscount(BigDecimal totalPrice, BigDecimal percentage) {
        if (percentage == null) {
            return BigDecimal.ZERO;
        }

        return totalPrice
                .multiply(percentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    private void updateEntities(LiquidationDto dto, Liquidation liquidation, boolean isUpdate) {

        Load load = loadService.getById(dto.getLoadId());

        if (!isUpdate) {
            if (!load.getState().equals(StateLoadEnum.PENDING)) {
                throw new InvalidValueException("Solo se puede crear una liquidación de una carga en estado pendiente");
            }
            if (liquidationRepository.existsByLoadIdAndActiveTrue(dto.getLoadId())) {
                throw new DuplicateException(LIQUIDATION, "Carga", load.getCorrelativeLotCode());
            }
            load.setState(StateLoadEnum.LIQUIDATED);
        } else {
            if (liquidationRepository.existsByLoadIdAndIdNotAndActiveTrue(dto.getLoadId(), liquidation.getId())) {
                throw new DuplicateException(LIQUIDATION, "Carga", load.getCorrelativeLotCode());
            }
        }

        if (dto.getSupplierId() != null) {
            load.setSupplier(supplierService.getById(dto.getSupplierId()));
        }
        if (dto.getMineId() != null) {
            load.setMine(mineService.getById(dto.getMineId()));
        }
        if (dto.getCooperativeId() != null) {
            load.setCooperative(cooperativeService.getById(dto.getCooperativeId()));
        }

        if (dto.getMineralId() != null) {
            load.setMineral(mineralService.getById(dto.getMineralId()));
        }

        if (dto.getTypeMineralId() != null) {
            load.setTypeMineral(typeMineralService.getById(dto.getTypeMineralId()));
        }

        if (dto.getMetricWetKilograms() != null) {
            load.setWeight(dto.getMetricWetKilograms());
        }


        loadRepository.save(load);
        liquidation.setLoad(load);
    }


    @Override
    @Transactional
    public LiquidationPojo update(Integer id, LiquidationDto dto) {
        Liquidation liquidation = this.getById(id);
        liquidationMapper.fromDto(dto, liquidation);
        updateEntities(dto, liquidation, true);
        calculateTotalPrice(liquidation);
        Liquidation savedLiquidation = liquidationRepository.save(liquidation);
        return liquidationMapper.toPojo(savedLiquidation);
    }


    @Override
    protected GenericRepository<Liquidation, Integer> getRepository() {
        return liquidationRepository;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Liquidation liquidation = this.getById(id);
        liquidation.setActive(false);
        Load load = liquidation.getLoad();
        if (load != null) {
            load.setState(StateLoadEnum.PENDING);
            loadRepository.save(load);
        }
        liquidationRepository.save(liquidation);
    }

    @Override
    public PagePojo<LiquidationPojo> getByPageAndFilters(int page, int size, String sortBy,
                                                         String sortOrder, LiquidationFilter filter) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Liquidation> spec = this.generateSpecification(filter);
        Page<Liquidation> filtered = liquidationRepository.findAll(spec, pageable);
        return liquidationMapper.fromPageToPagePojo(filtered);
    }

    @Override
    public RoyaltyCalculationPojo calculateRoyalties(RoyaltyCalculationDto request) {

        BigDecimal weightInKilos = request.getDryMetricTonnes();

        // -----------------------------------------------------
        // 1. CÁLCULO ZINC
        //[(Kilos * 2.2046) * (Ley/100) * Cotización * TC * 3%]
        // -----------------------------------------------------
        BigDecimal poundsZinc = weightInKilos.multiply(LBS_PER_KG);
        BigDecimal finePoundsZinc = poundsZinc.multiply(request.getLawZinc().divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));

        BigDecimal grossValueZinc = finePoundsZinc
                .multiply(request.getQuotationZinc())
                .multiply(request.getExchangeRate());

        BigDecimal royaltyZinc = grossValueZinc.multiply(ALICUOTA_ZINC).setScale(2, RoundingMode.HALF_UP);


        // -----------------------------------------------------
        // 2. CÁLCULO PLATA
        // Fórmula: [(Peso Kilo / 1000) * (Ley * 100) / 31.1035 * Cot * TC * 3.6%]
        // -----------------------------------------------------

        BigDecimal weightInTonnes = weightInKilos.divide(new BigDecimal("1000"), 5, RoundingMode.HALF_UP);

        // Paso B: Gramos de plata
        BigDecimal gramsSilver = weightInTonnes.multiply(request.getLawSilver().multiply(new BigDecimal("100")));

        // Paso C: Onzas Troy
        BigDecimal troyOuncesSilver = gramsSilver.divide(GRAMS_PER_TROY_OZ, 4, RoundingMode.HALF_UP);

        // Paso D: Valor Bruto
        BigDecimal grossValueSilver = troyOuncesSilver
                .multiply(request.getQuotationSilver())
                .multiply(request.getExchangeRate());

        BigDecimal royaltySilver = grossValueSilver.multiply(ALICUOTA_PLATA).setScale(2, RoundingMode.HALF_UP);


        // -----------------------------------------------------
        // 3. CÁLCULO PLOMO
        // -----------------------------------------------------
        BigDecimal poundsLead = weightInKilos.multiply(LBS_PER_KG);
        BigDecimal finePoundsLead = poundsLead.multiply(request.getLawLead().divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));

        BigDecimal grossValueLead = finePoundsLead
                .multiply(request.getQuotationLead())
                .multiply(request.getExchangeRate());

        BigDecimal royaltyLead = grossValueLead.multiply(ALICUOTA_PLOMO).setScale(2, RoundingMode.HALF_UP);

        return RoyaltyCalculationPojo.builder()
                .royaltyZinc(royaltyZinc)
                .royaltySilver(royaltySilver)
                .royaltyLead(royaltyLead)
                .build();
    }


    @Override
    public byte[] generateLiquidationPdf(Integer idLiquidation) {

        this.getById(idLiquidation);

        try {
            InputStream reportStream =
                    getClass().getResourceAsStream("/reports/emcar_report.jrxml");

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(reportStream);

            Map<String, Object> params = new HashMap<>();
            params.put("idLiquidation", idLiquidation);

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

    @Override
    public List<LiquidationPojo> getFiltered(LiquidationFilter filter) {
        Specification<Liquidation> spec = this.generateSpecification(filter);
        List<Liquidation> filtered = liquidationRepository.findAll(spec);
        return liquidationMapper.toPojoList(filtered);
    }


    private Specification<Liquidation> generateSpecification(LiquidationFilter filter) {
        FieldsFilterUtil fields = new FieldsFilterUtil();
        fields.addEqualsField("active", true);
        fields.addLikeField("load.correlativeLotCode", filter.getCorrelativeLotCode());
        fields.addLikeField("load.supplier.name", filter.getSupplierName());
        fields.addDateField("liquidationDate", filter.getStartDate()
                , filter.getEndDate() != null ? filter.getEndDate() : LocalDate.now());
        if (filter.getSome() != null && !filter.getSome().isBlank()) {
            List<Integer> someIds = liquidationRepository.findIdsBySome(filter.getSome());
            fields.addInSomeField("id", someIds);
        }
        return SpecificationUtils.createSpecification(fields.getFilterFields());
    }
}
