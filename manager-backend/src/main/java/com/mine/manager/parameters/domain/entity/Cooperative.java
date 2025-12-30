package com.mine.manager.parameters.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "cooperatives", schema = "mine")
public class Cooperative extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 250, nullable = false)
    private String name;
    private Integer cajaNacional;
    private Integer fedecomin;
    private Integer fencomin;
    private Integer comibol;
    private Integer wilstermann;
    private Integer cooperativeContribution;
    private Integer miningRoyalties;
    private Boolean printCajaNacional;
    private Boolean printFedecomin;
    private Boolean printFencomin;
    private Boolean printComibol;
    private Boolean printWilstermann;
    private Boolean printCooperativeContribution;
    private Boolean printMiningRoyalties;
}
