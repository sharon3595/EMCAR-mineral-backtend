package com.mine.manager.parameters.presentation.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MineralDto {


    @Size(max = 250, message = "{mineralDto.name.size}")
    private String name;

    @NotBlank(message = "{mineralDto.symbol.not-blank}")
    @Size(max = 250, message = "{mineralDto.symbol.size}")
    private String symbol;
}