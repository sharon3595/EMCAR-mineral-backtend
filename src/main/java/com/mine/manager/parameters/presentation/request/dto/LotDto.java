package com.mine.manager.parameters.presentation.request.dto;

import com.mine.manager.common.enums.LotTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LotDto {

    @NotBlank(message = "{lotDto.prefix.not-blank}")
    @Size(max = 30, message = "{lotDto.prefix.size}")
    private String prefix;
    @NotBlank(message = "{lotDto.description.not-blank}")
    @Size(max = 300, message = "{lotDto.description.size}")
    private String description;
    @NotNull(message = "{lotDto.initialDocNumber.not-null}")
    private Integer initialDocNumber;
    @NotNull(message = "{lotDto.assignment.not-null}")
    private LotTypeEnum assignment;
    private Boolean state=true;
}

