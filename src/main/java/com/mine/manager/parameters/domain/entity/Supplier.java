package com.mine.manager.parameters.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Data
@Table(name = "suppliers", schema = "mine")
public class Supplier extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 250, nullable = false)
    private String name;

    @Column(length = 250)
    private String surname;

    @Column(length = 150, unique = true)
    private String documentNumber;

    @Column(length = 200)
    private String address;

    @Column(length = 20/*, nullable = false*/)
    private String expeditionPlace;
}
