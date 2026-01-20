package com.mine.manager.parameters.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "type_minerals", schema = "mine")
public class TypeMineral extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 250)
    private String name;

    @Column(length = 250, nullable = false)
    private String symbol;
}
