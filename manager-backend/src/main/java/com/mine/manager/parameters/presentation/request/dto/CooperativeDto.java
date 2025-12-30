package com.mine.manager.parameters.presentation.request.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CooperativeDto {

    @NotBlank(message = "{cooperativeDto.name.not-blank}")
    private String name;

    @Min(value = 0, message = "{cooperativeDto.percentage.range}")
    @Max(value = 100, message = "{cooperativeDto.percentage.range}")
    private Integer cajaNacional = 0;

    @Min(value = 0, message = "{cooperativeDto.percentage.range}")
    @Max(value = 100, message = "{cooperativeDto.percentage.range}")
    private Integer fedecomin = 0;

    @Min(value = 0, message = "{cooperativeDto.percentage.range}")
    @Max(value = 100, message = "{cooperativeDto.percentage.range}")
    private Integer fencomin = 0;

    @Min(value = 0, message = "{cooperativeDto.percentage.range}")
    @Max(value = 100, message = "{cooperativeDto.percentage.range}")
    private Integer comibol = 0;

    @Min(value = 0, message = "{cooperativeDto.percentage.range}")
    @Max(value = 100, message = "{cooperativeDto.percentage.range}")
    private Integer wilstermann = 0;

    @Min(value = 0, message = "{cooperativeDto.percentage.range}")
    @Max(value = 100, message = "{cooperativeDto.percentage.range}")
    private Integer cooperativeContribution = 0;

    @Min(value = 0, message = "{cooperativeDto.percentage.range}")
    @Max(value = 100, message = "{cooperativeDto.percentage.range}")
    private Integer miningRoyalties = 0;
    private Boolean printCajaNacional = false;
    private Boolean printFedecomin = false;
    private Boolean printFencomin = false;
    private Boolean printComibol = false;
    private Boolean printWilstermann = false;
    private Boolean printCooperativeContribution = false;
    private Boolean printMiningRoyalties = false;
}

