package com.mine.manager.parameters.domain.entity;

import com.mine.manager.common.enums.LotTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "lots", schema = "mine")
public class Lot extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 30, nullable = false)
    private String prefix;
    @Column(length = 300, nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer initialDocNumber;
    private Integer currentDocNumber;
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private LotTypeEnum assignment;
    private Boolean state;
}