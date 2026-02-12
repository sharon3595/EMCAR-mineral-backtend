package com.mine.manager.parameters.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@NoArgsConstructor
@Data
@Table(name = "percentageLiquidations", schema = "mine")
public class PercentageLiquidation extends Base {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 5, scale = 2)
    private BigDecimal cajaNacional;
    @Column(precision = 5, scale = 2)
    private BigDecimal fedecomin;
    @Column(precision = 5, scale = 2)
    private BigDecimal fencomin;
    @Column(precision = 5, scale = 2)
    private BigDecimal comibol;
    @Column(precision = 5, scale = 2)
    private BigDecimal miningRoyalties;


    @OneToOne(optional = false)
    @JoinColumn(name = "load_id", unique = true, nullable = false)
    private Load load;


    @Column(nullable = false)
    private LocalDate admissionDate;


    @Column(nullable = false)
    private LocalDate percentageLiquidationDate;


    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal exchangeRate;


    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal quotation;


    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal percentage;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal humidityPercentage;


    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal metricWetKilograms;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal dmSilver;


    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal dryMetricKilograms;


    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal firstAdvance;


    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal transportation;


    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amountPayableBc;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amountPayableTc;


    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;


    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal discounts;
}

