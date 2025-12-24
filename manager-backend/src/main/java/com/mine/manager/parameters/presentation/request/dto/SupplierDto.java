package com.mine.manager.parameters.presentation.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SupplierDto {

    private Integer id;

    @NotBlank(message = "{supplier.name.not-blank}")
    @Size(min = 3, max = 250, message = "{supplier.name.size}")
    private String name;

    @Size(min = 3, max = 250, message = "{supplier.surname.size}")
    private String surname;

    @Size(max = 150, message = "{supplier.documentNumber.size}")
    private String documentNumber;

    @Size(max = 200, message = "{supplier.address.size}")
    private String address;

    @NotBlank(message = "{supplier.supplierGroup.not-blank}")
    @Size(max = 150, message = "{supplier.supplierGroup.size}")
    private String supplierGroup;

    @Size(max = 20, message = "{supplier.expeditionPlace.size}")
    private String expeditionPlace;
}
