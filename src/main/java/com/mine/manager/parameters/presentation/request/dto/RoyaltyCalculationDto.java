package com.mine.manager.parameters.presentation.request.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RoyaltyCalculationDto {

    @NotNull(message = "El peso seco es obligatorio")
    @PositiveOrZero
    private BigDecimal dryMetricTonnes;

    @NotNull(message = "El tipo de cambio es obligatorio")
    @PositiveOrZero
    private BigDecimal exchangeRate;

    @PositiveOrZero
    private BigDecimal lawSilver = BigDecimal.ZERO;

    @PositiveOrZero
    private BigDecimal lawZinc = BigDecimal.ZERO;

    @PositiveOrZero
    private BigDecimal lawLead = BigDecimal.ZERO;

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 3, fraction = 2, message = "{liquidation.quotation.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal quotationSilver = BigDecimal.ZERO;

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 3, fraction = 2, message = "{liquidation.quotation.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal quotationZinc = BigDecimal.ZERO;

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 3, fraction = 2, message = "{liquidation.quotation.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal quotationLead = BigDecimal.ZERO;

}
