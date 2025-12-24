package com.mine.manager.parameters.presentation.request.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SupplierFilter {

    private String name;
    private String surname;
    private String documentNumber;
    private String address;
    private String personGroup;
    private String some;
}
