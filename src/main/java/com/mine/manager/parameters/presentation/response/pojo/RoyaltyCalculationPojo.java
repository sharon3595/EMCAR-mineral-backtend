package com.mine.manager.parameters.presentation.response.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoyaltyCalculationPojo {
    private BigDecimal royaltySilver;
    private BigDecimal royaltyZinc;
    private BigDecimal royaltyLead;
}