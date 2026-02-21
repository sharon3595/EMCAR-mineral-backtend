package com.mine.manager.parameters.presentation.request.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class CooperativeDto {

    @NotBlank(message = "{cooperativeDto.name.not-blank}")
    private String name;

    @DecimalMin(value = "0.00", message = "{cooperativeDto.percentage.range}")
    @DecimalMax(value = "100.00", message = "{cooperativeDto.percentage.range}")
    @Digits(integer = 3, fraction = 2, message = "{cooperativeDto.percentage.digits}")
    private BigDecimal cajaNacional = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "{cooperativeDto.percentage.range}")
    @DecimalMax(value = "100.00", message = "{cooperativeDto.percentage.range}")
    @Digits(integer = 3, fraction = 2, message = "{cooperativeDto.percentage.digits}")
    private BigDecimal fedecomin = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "{cooperativeDto.percentage.range}")
    @DecimalMax(value = "100.00", message = "{cooperativeDto.percentage.range}")
    @Digits(integer = 3, fraction = 2, message = "{cooperativeDto.percentage.digits}")
    private BigDecimal fencomin = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "{cooperativeDto.percentage.range}")
    @DecimalMax(value = "100.00", message = "{cooperativeDto.percentage.range}")
    @Digits(integer = 3, fraction = 2, message = "{cooperativeDto.percentage.digits}")
    private BigDecimal comibol = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "{cooperativeDto.percentage.range}")
    @DecimalMax(value = "100.00", message = "{cooperativeDto.percentage.range}")
    @Digits(integer = 3, fraction = 2, message = "{cooperativeDto.percentage.digits}")
    private BigDecimal wilstermann = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "{cooperativeDto.percentage.range}")
    @DecimalMax(value = "100.00", message = "{cooperativeDto.percentage.range}")
    @Digits(integer = 3, fraction = 2, message = "{cooperativeDto.percentage.digits}")
    private BigDecimal cooperativeContribution = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "{cooperativeDto.percentage.range}")
    @DecimalMax(value = "100.00", message = "{cooperativeDto.percentage.range}")
    @Digits(integer = 3, fraction = 2, message = "{cooperativeDto.percentage.digits}")
    private BigDecimal miningRoyalties = BigDecimal.ZERO;
    private Boolean printCajaNacional = true;
    private Boolean printFedecomin = true;
    private Boolean printFencomin = true;
    private Boolean printComibol = true;
    private Boolean printWilstermann = true;
    private Boolean printCooperativeContribution = true;
    private Boolean printMiningRoyalties = true;
}

