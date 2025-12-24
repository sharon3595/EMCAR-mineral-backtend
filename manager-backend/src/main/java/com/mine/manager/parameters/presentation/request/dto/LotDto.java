package com.mine.manager.parameters.presentation.request.dto;

import com.mine.manager.parameters.domain.entity.Base;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LotDto extends Base {

    @NotBlank(message = "{lotDto.prefix.not-blank}")
    @Size(max = 30, message = "{lotDto.prefix.size}")
    private String prefix;
    @NotBlank(message = "{lotDto.name.not-blank}")
    @Size(max = 300, message = "{lotDto.description.size}")
    private String description;
    @NotNull(message = "{lotDto.name.not-null}")
    private Integer initialDocNumber;
    @NotNull(message = "{lotDto.assignment.not-null}")
    private String assignment;
    private Boolean state=true;
}

