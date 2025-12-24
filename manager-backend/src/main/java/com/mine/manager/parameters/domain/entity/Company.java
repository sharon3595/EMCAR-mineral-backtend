package com.mine.manager.parameters.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "companies", schema = "mine")
public class Company extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 200, nullable = false)
    private String socialReason;
    @Column(length = 30, nullable = false)
    private Integer nit;
    @Column(length = 30, nullable = false)
    private String code;
    @Column(length = 500)
    private String purpose;
    private String nim;
    @Column(length = 500)
    private String address;
    @Column(length = 30)
    private String cellphone;

    @Column(columnDefinition = "TEXT")
    private String logo;

}
