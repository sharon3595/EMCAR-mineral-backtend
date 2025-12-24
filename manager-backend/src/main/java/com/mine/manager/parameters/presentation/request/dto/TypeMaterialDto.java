package com.mine.manager.parameters.presentation.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TypeMaterialDto {

    @NotBlank(message = "{typeMaterialDto.name.not-blank}")
    @Size(max = 250, message = "{typeMaterialDto.name.size}")
    private String name;

    @Size(max = 500, message = "{typeMaterialDto.description.size}")
    private String description;
}