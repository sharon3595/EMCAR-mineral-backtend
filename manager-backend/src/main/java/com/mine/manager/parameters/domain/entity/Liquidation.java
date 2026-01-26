package com.mine.manager.parameters.domain.entity;

import com.mine.manager.common.enums.LiquidationTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
@Table(name = "liquidations", schema = "mine")
public class Liquidation extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private LiquidationTypeEnum liquidationTypeEnum;
    @Column(precision = 5, scale = 2)
    private BigDecimal cajaNacional;
    @Column(precision = 5, scale = 2)
    private BigDecimal fedecomin;
    @Column(precision = 5, scale = 2)
    private BigDecimal fencomin;
    @Column(precision = 5, scale = 2)
    private BigDecimal comibol;
    @Column(precision = 5, scale = 2)
    private BigDecimal cooperativeContribution;
    @Column(precision = 5, scale = 2)
    private BigDecimal miningRoyalties;

    @OneToOne(optional = false)
    @JoinColumn(name = "load_id", unique = true, nullable = false)
    private Load load;

    @Column(nullable = false)
    private LocalDate admissionDate;

    @Column(nullable = false)
    private LocalDate liquidationDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal exchangeRate;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal priceSilver;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal priceZinc;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal priceLead;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal lawSilver;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal lawZinc;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal lawLead;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal humidityPercentage;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal metricWetKilograms;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal dryMetricKilograms;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal quotationSilver;
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal quotationZinc;
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal quotationLead;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal royaltySilver;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal royaltyZinc;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal royaltyLead;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal firstAdvance;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal secondAdvance;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal transportationBonus;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amountPayableBc;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amountPayableTc;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal discounts;
}
