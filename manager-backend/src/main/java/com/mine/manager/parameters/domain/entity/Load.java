package com.mine.manager.parameters.domain.entity;

import com.mine.manager.common.enums.StateLoadEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
@Table(name = "loads", schema = "mine")
public class Load extends Base{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "lot_id", nullable = false)
    private Lot lot;

    @Column(length = 20, nullable = false)
    private String externalLot;

    @Column(length = 50, nullable = false)
    private String correlativeLotCode;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToOne
    @JoinColumn(name = "type_material_id", nullable = false)
    private TypeMaterial typeMaterial;

    @ManyToOne
    @JoinColumn(name = "mine_id")
    private Mine mine;

    @ManyToOne
    @JoinColumn(name = "cooperative_id")
    private Cooperative cooperative;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal numberSacks;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal weight;

    @Column(length = 500)
    private String observation;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private StateLoadEnum state;

    @OneToOne(mappedBy = "load", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Liquidation liquidation;
}
