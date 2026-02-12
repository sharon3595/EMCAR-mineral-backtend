package com.mine.manager.parameters.presentation.request.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PercentageLiquidationDto {

    @NotNull(message = "{liquidation.loadId.notNull}")
    @Schema(description = "ID de la carga asociada", example = "10")
    private Integer loadId;

    @Schema(description = "ID del proveedor (Opcional)", example = "4")
    private Integer supplierId;

    // --- FECHAS ---

    @NotNull(message = "{liquidation.admissionDate.notNull}")
    @Schema(description = "Fecha de ingreso", example = "2026-01-10")
    private LocalDate admissionDate;

    @NotNull(message = "{liquidation.liquidationDate.notNull}")
    @Schema(description = "Fecha de liquidación", example = "2026-01-12")
    private LocalDate liquidationDate;

    // --- CAMBIO DEL DOLAR ---

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

    // --- PESOS Y HUMEDAD ---

    @NotNull(message = "{liquidation.humidity.notNull}")
    @DecimalMin(value = "0.00", message = "{liquidation.amount.positive}")
    @DecimalMax(value = "100.00", message = "{liquidation.percentage.max}")
    @Digits(integer = 3, fraction = 2, message = "{liquidation.percentage.digits}")
    @Schema(description = "Porcentaje de humedad (0-100)", example = "8.50")
    private BigDecimal humidityPercentage;

    @NotNull(message = "{liquidation.weight.notNull}")
    @Positive(message = "{liquidation.weight.positive}")
    @Digits(integer = 12, fraction = 3, message = "{liquidation.weight.digits}")
    @Schema(description = "Peso en Kilogramos Métricos Húmedos", example = "14.500")
    private BigDecimal metricWetKilograms; // KMH


    // --- APORTES Y DESCUENTOS (Porcentajes) ---

    @DecimalMin(value = "0.00", message = "{liquidation.amount.positive}")
    @DecimalMax(value = "100.00", message = "{liquidation.percentage.max}")
    @Digits(integer = 3, fraction = 2, message = "{liquidation.percentage.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal cajaNacional = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "{liquidation.amount.positive}")
    @DecimalMax(value = "100.00", message = "{liquidation.percentage.max}")
    @Digits(integer = 3, fraction = 2, message = "{liquidation.percentage.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal fedecomin = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "{liquidation.amount.positive}")
    @DecimalMax(value = "100.00", message = "{liquidation.percentage.max}")
    @Digits(integer = 3, fraction = 2, message = "{liquidation.percentage.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal fencomin = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "{liquidation.amount.positive}")
    @DecimalMax(value = "100.00", message = "{liquidation.percentage.max}")
    @Digits(integer = 3, fraction = 2, message = "{liquidation.percentage.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal comibol = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "{liquidation.amount.positive}")
    @DecimalMax(value = "100.00", message = "{liquidation.percentage.max}")
    @Digits(integer = 3, fraction = 2, message = "{liquidation.percentage.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal miningRoyalties = BigDecimal.ZERO;


    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 10, fraction = 2, message = "{liquidation.money.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal firstAdvance = BigDecimal.ZERO;

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 10, fraction = 2, message = "{liquidation.money.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal transportation = BigDecimal.ZERO;

}