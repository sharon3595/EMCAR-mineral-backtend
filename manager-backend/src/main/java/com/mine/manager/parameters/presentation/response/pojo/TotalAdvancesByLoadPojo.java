package com.mine.manager.parameters.presentation.response.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TotalAdvancesByLoadPojo {

    private Integer loadId;
    private BigDecimal totalAmount;
}
