package com.mine.manager.parameters.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
@Table(name = "cooperatives", schema = "mine")
public class Cooperative extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 250, nullable = false)
    private String name;

    @Column(precision = 5, scale = 2)
    private BigDecimal cajaNacional;
    @Column(precision = 5, scale = 2)
    private BigDecimal fedecomin;
    @Column(precision = 5, scale = 2)
    private BigDecimal fencomin;
    @Column(precision = 5, scale = 2)
    private BigDecimal comibol;
    @Column(precision = 5, scale = 2)
    private BigDecimal wilstermann;
    @Column(precision = 5, scale = 2)
    private BigDecimal cooperativeContribution;
    @Column(precision = 5, scale = 2)
    private BigDecimal miningRoyalties;

    private Boolean printCajaNacional;
    private Boolean printFedecomin;
    private Boolean printFencomin;
    private Boolean printComibol;
    private Boolean printWilstermann;
    private Boolean printCooperativeContribution;
    private Boolean printMiningRoyalties;
}
