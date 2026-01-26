package com.mine.manager.parameters.domain.mapper;

import com.mine.manager.parameters.domain.entity.Liquidation;
import com.mine.manager.parameters.presentation.request.dto.LiquidationDto;
import com.mine.manager.parameters.presentation.response.pojo.LiquidationPojo;
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
public class LiquidationMapper {


    public Liquidation fromDto(LiquidationDto dto, Liquidation liquidation) {

        liquidation.setAdmissionDate(dto.getAdmissionDate());
        liquidation.setLiquidationDate(dto.getLiquidationDate());
        liquidation.setExchangeRate(dto.getExchangeRate());

        // 2. PRECIOS
        liquidation.setPriceSilver(dto.getPriceSilver());
        liquidation.setPriceZinc(dto.getPriceZinc());
        liquidation.setPriceLead(dto.getPriceLead());

        // 3. LEYES

        liquidation.setLawSilver(dto.getLawSilver());
        liquidation.setLawZinc(dto.getLawZinc());
        liquidation.setLawLead(dto.getLawLead());


        // 4. PESOS Y CÁLCULO DE T.M.S.

        liquidation.setHumidityPercentage(dto.getHumidityPercentage());
        liquidation.setMetricWetKilograms(dto.getMetricWetKilograms());

        // LÓGICA: KMS = KMH * ((100 - Humedad) / 100)
        if (dto.getMetricWetKilograms() != null && dto.getHumidityPercentage() != null) {
            BigDecimal humidityFactor = BigDecimal.valueOf(100)
                    .subtract(dto.getHumidityPercentage())
                    .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

            BigDecimal tms = dto.getMetricWetKilograms().multiply(humidityFactor);
            liquidation.setDryMetricKilograms(tms.setScale(3, RoundingMode.HALF_UP));
        }

        // 5. COTIZACIONES Y REGALÍAS

        liquidation.setQuotationSilver(dto.getQuotationSilver());
        liquidation.setQuotationZinc(dto.getQuotationZinc());
        liquidation.setQuotationLead(dto.getQuotationLead());

        liquidation.setRoyaltySilver(dto.getRoyaltySilver());
        liquidation.setRoyaltyZinc(dto.getRoyaltyZinc());
        liquidation.setRoyaltyLead(dto.getRoyaltyLead());

        // 6. APORTES Y DESCUENTOS (Porcentajes)

        liquidation.setCajaNacional(dto.getCajaNacional());
        liquidation.setFedecomin(dto.getFedecomin());
        liquidation.setFencomin(dto.getFencomin());
        liquidation.setComibol(dto.getComibol());
        liquidation.setCooperativeContribution(dto.getCooperativeContribution());
        liquidation.setMiningRoyalties(dto.getMiningRoyalties());


        // 7. ANTICIPOS Y BONOS

        liquidation.setSecondAdvance(dto.getSecondAdvance());
        liquidation.setTransportationBonus(dto.getTransportationBonus());

        liquidation.setLiquidationTypeEnum(dto.getLiquidationType());
        return liquidation;

    }


    public LiquidationPojo toPojo(Liquidation liquidation) {
        return new LiquidationPojo(liquidation);
    }


    public List<LiquidationPojo> toPojoList(List<Liquidation> liquidations) {
        return liquidations.stream()
                .map(this::toPojo)
                .collect(Collectors.toList());
    }


    public PagePojo<LiquidationPojo> fromPageToPagePojo(Page<Liquidation> page) {
        PagePojo<LiquidationPojo> dto = new PagePojo<>();
        dto.setContent(toPojoList(page.getContent()));
        dto.setLast(page.isLast());
        dto.setPageNumber(page.getNumber());
        dto.setPageSize(page.getSize());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        return dto;
    }
}

