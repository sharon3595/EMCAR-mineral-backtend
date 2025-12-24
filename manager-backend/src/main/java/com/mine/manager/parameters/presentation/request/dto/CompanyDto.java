package com.mine.manager.parameters.presentation.request.dto;

import com.mine.manager.common.customvalidation.IsBase64;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyDto {

    @NotBlank(message = "{company.code.not-blank}")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "{company.code.pattern}")
    private String code;
    @NotBlank(message = "{company.social-reason.not-blank}")
    @Pattern(regexp = "^[\\p{L} \\p{M}.]+$", message = "{company.social-reason.pattern}")
    private String socialReason;
    @NotNull(message = "{company.nit.not-null}")
    private Integer nit;

    @NotBlank(message = "{company.purpose.not-blank}")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{company.purpose.pattern}")
    private String purpose;

    @NotBlank(message = "{company.logo.not-blank}")
    @IsBase64(message = "{company.logo.base64}")
    private String logo;

    private String nim;

    private String address;

    private String cellphone;
}