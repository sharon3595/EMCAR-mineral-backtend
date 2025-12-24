package com.mine.manager.parameters.presentation.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MineDto {

    @NotBlank(message = "{mine.name.not-blank}")
    @Size(max = 250, message = "{mine.name.size}")
    private String name;

    @Size(max = 500, message = "{mine.description.size}")
    private String description;
}