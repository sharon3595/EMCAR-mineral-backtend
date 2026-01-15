package com.mine.manager.parameters.presentation.request.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class LiquidationFilter {

    private String supplierName;
    private String correlativeLotCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private String some;
}
