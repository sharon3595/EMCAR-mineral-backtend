package com.mine.manager.parameters.presentation.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TypeMineralDto {

    @NotBlank(message = "{typeMineralDto.name.not-blank}")
    @Size(max = 250, message = "{typeMineralDto.name.size}")
    private String name;

    @Size(max = 250, message = "{typeMineralDto.symbol.size}")
    private String symbol;
}