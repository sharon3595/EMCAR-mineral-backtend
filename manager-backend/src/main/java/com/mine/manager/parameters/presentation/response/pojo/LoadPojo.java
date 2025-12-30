package com.mine.manager.parameters.presentation.response.pojo;

import com.mine.manager.parameters.domain.entity.Load;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LoadPojo {
    private Integer id;
    private Boolean active;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer supplierId;
    private Integer lotId;
    private Integer materialId;
    private Integer typeMaterialId;
    private Integer mineId;
    private Integer cooperativeId;
    private LocalDate date;
    private String externalLot;
    private String correlativeLotCode;
    private BigDecimal numberSacks;
    private BigDecimal weight;
    private String observation;

    public LoadPojo(Load load) {
        this.id = load.getId();
        this.active = load.getActive();
        this.createdBy = load.getCreatedBy();
        this.updatedBy = load.getUpdatedBy();
        this.createdAt = load.getCreatedAt();
        this.updatedAt = load.getUpdatedAt();

        this.date = load.getDate();
        this.externalLot = load.getExternalLot();
        this.correlativeLotCode = load.getCorrelativeLotCode();
        this.numberSacks = load.getNumberSacks();
        this.weight = load.getWeight();
        this.observation = load.getObservation();

        this.supplierId = (load.getSupplier() != null) ? load.getSupplier().getId() : null;
        this.lotId = (load.getLot() != null) ? load.getLot().getId() : null;
        this.materialId = (load.getMaterial() != null) ? load.getMaterial().getId() : null;
        this.typeMaterialId = (load.getTypeMaterial() != null) ? load.getTypeMaterial().getId() : null;

        this.mineId = (load.getMine() != null) ? load.getMine().getId() : null;
        this.cooperativeId = (load.getCooperative() != null) ? load.getCooperative().getId() : null;
    }
}