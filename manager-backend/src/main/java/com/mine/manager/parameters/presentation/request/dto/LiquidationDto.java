package com.mine.manager.parameters.presentation.request.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.mine.manager.common.enums.LiquidationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class LiquidationDto {

    @NotNull(message = "{liquidation.liquidationType.not-null}")
    private LiquidationTypeEnum liquidationType;

    @NotNull(message = "{liquidation.loadId.notNull}")
    @Schema(description = "ID de la carga asociada", example = "10")
    private Integer loadId;

    @Schema(description = "ID del proveedor (Opcional)", example = "4")
    private Integer supplierId;

    @Schema(description = "ID de la mina (Opcional)", example = "1")
    private Integer mineId;

    @Schema(description = "ID de la cooperativa (Opcional)", example = "4")
    private Integer cooperativeId;

    @Schema(description = "ID del mineral", example = "3")
    private Integer mineralId;

    @Schema(description = "ID del tipo de mineral", example = "2")
    private Integer typeMineralId;
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

    // --- PRECIOS DEL MINERAL ---

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 10, fraction = 2, message = "{liquidation.price.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal priceSilver = BigDecimal.ZERO;

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 10, fraction = 2, message = "{liquidation.price.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal priceZinc = BigDecimal.ZERO;

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 10, fraction = 2, message = "{liquidation.price.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal priceLead = BigDecimal.ZERO;

    // --- LEYES DEL MINERAL ---

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 8, fraction = 2, message = "{liquidation.law.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal lawSilver = BigDecimal.ZERO;

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 8, fraction = 2, message = "{liquidation.law.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal lawZinc = BigDecimal.ZERO;

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 8, fraction = 2, message = "{liquidation.law.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal lawLead = BigDecimal.ZERO;

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
    @Schema(description = "Peso en Toneladas Métricas Húmedas", example = "14.500")
    private BigDecimal metricWetTonnes; // TMH

    // --- COTIZACIONES OFICIALES (Para Regalías) ---

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

    // --- MONTOS CALCULADOS DE REGALÍAS ---

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 10, fraction = 2, message = "{liquidation.money.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal royaltySilver = BigDecimal.ZERO;

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 10, fraction = 2, message = "{liquidation.money.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal royaltyZinc = BigDecimal.ZERO;

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 10, fraction = 2, message = "{liquidation.money.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal royaltyLead = BigDecimal.ZERO;

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
    private BigDecimal cooperativeContribution = BigDecimal.ZERO;

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
    private BigDecimal secondAdvance = BigDecimal.ZERO;

    @PositiveOrZero(message = "{liquidation.amount.positive}")
    @Digits(integer = 10, fraction = 2, message = "{liquidation.money.digits}")
    @JsonSetter(nulls = Nulls.SKIP)
    private BigDecimal transportationBonus = BigDecimal.ZERO;

}