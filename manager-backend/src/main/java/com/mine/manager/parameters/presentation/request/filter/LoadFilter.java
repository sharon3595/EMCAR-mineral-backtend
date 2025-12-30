package com.mine.manager.parameters.presentation.request.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class LoadFilter {

    private String supplierName;
    private String correlativeLotCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private String lotDescription;
    private String some;

}
