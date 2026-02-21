package com.mine.manager.parameters.presentation.request.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiquidPayableCalculationDto {

    @NotNull(message = "{liquidation.exchangeRate.notNull}")
    @Positive(message = "{liquidation.exchangeRate.positive}")
    @Digits(integer = 8, fraction = 2, message = "{liquidation.exchangeRate.digits}")
    @Schema(description = "Tipo de cambio oficial", example = "6.96")
    private BigDecimal exchangeRate;

    // --- Cotización y porcentaje ---


    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 8, fraction = 2, message = "{percentageLiquidation.quotation.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal quotation = BigDecimal.ZERO;

    @NotNull(message = "{liquidation.percentage.notNull}")
    @DecimalMin(value = "0.00", message = "{liquidation.amount.positive}")
    @DecimalMax(value = "100.00", message = "{liquidation.percentage.max}")
    @Digits(integer = 3, fraction = 2, message = "{liquidation.percentage.digits}")
    @Schema(description = "Porcentaje (0-100)", example = "8.50")
    private BigDecimal percentage;

    // --- DM SILVER ---

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 8, fraction = 2, message = "{percentageLiquidation.dmSilver.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal dmSilver = BigDecimal.ZERO;

    @NotNull(message = "El peso seco en kilos es obligatorio")
    @PositiveOrZero
    private BigDecimal dryMetricKilograms;
}
