package com.mine.manager.parameters.presentation.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MaterialDto {

    @NotBlank(message = "{materialDto.name.not-blank}")
    @Size(max = 250, message = "{materialDto.name.size}")
    private String name;

    @Size(max = 500, message = "{materialDto.description.size}")
    private String description;
}