package com.mine.manager.parameters.domain.mapper;


import com.mine.manager.parameters.domain.entity.PercentageLiquidation;
import com.mine.manager.parameters.presentation.request.dto.PercentageLiquidationDto;
import com.mine.manager.parameters.presentation.response.pojo.PercentageLiquidationPojo;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class PercentageLiquidationMapper {


    public PercentageLiquidation fromDto(PercentageLiquidationDto dto, PercentageLiquidation percentageLiquidation) {


        percentageLiquidation.setAdmissionDate(dto.getAdmissionDate());
        percentageLiquidation.setPercentageLiquidationDate(dto.getLiquidationDate());
        percentageLiquidation.setExchangeRate(dto.getExchangeRate());


        // 2. Porcentuales de sacos
        percentageLiquidation.setQuotation(dto.getQuotation());
        percentageLiquidation.setPercentage(dto.getPercentage());
        percentageLiquidation.setDmSilver(dto.getDmSilver());


        // 4. PESOS Y CÁLCULO DE T.M.S.


        percentageLiquidation.setHumidityPercentage(dto.getHumidityPercentage());
        percentageLiquidation.setMetricWetKilograms(dto.getMetricWetKilograms());


        // LÓGICA: KMS = KMH * ((100 - Humedad) / 100)
        if (dto.getMetricWetKilograms() != null && dto.getHumidityPercentage() != null) {
            BigDecimal humidityFactor = BigDecimal.valueOf(100)
                    .subtract(dto.getHumidityPercentage())
                    .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);


            BigDecimal tms = dto.getMetricWetKilograms().multiply(humidityFactor);
            percentageLiquidation.setDryMetricKilograms(tms.setScale(3, RoundingMode.HALF_UP));
        }


        // 6. APORTES Y DESCUENTOS (Porcentajes)


        percentageLiquidation.setCajaNacional(dto.getCajaNacional());
        percentageLiquidation.setFedecomin(dto.getFedecomin());
        percentageLiquidation.setFencomin(dto.getFencomin());
        percentageLiquidation.setComibol(dto.getComibol());
        percentageLiquidation.setMiningRoyalties(dto.getMiningRoyalties());


        // 7. ANTICIPOS Y BONOS


        percentageLiquidation.setTransportation(dto.getTransportation());

        return percentageLiquidation;


    }


    public PercentageLiquidationPojo toPojo(PercentageLiquidation percentageLiquidation) {
        return new PercentageLiquidationPojo(percentageLiquidation);
    }


    public List<PercentageLiquidationPojo> toPojoList(List<PercentageLiquidation> percentageLiquidations) {
        return percentageLiquidations.stream()
                .map(this::toPojo)
                .collect(Collectors.toList());
    }


    public PagePojo<PercentageLiquidationPojo> fromPageToPagePojo(Page<PercentageLiquidation> page) {
        PagePojo<PercentageLiquidationPojo> dto = new PagePojo<>();
        dto.setContent(toPojoList(page.getContent()));
        dto.setLast(page.isLast());
        dto.setPageNumber(page.getNumber());
        dto.setPageSize(page.getSize());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        return dto;
    }
}
