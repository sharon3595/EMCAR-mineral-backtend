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
    private Integer mineralId;
    private Integer typeMineralId;
    private Integer mineId;
    private Integer cooperativeId;
    private LocalDate date;
    private String externalLot;
    private String correlativeLotCode;
    private BigDecimal numberSacks;
    private BigDecimal weight;
    private String observation;

    private String supplierName;
    private String lotDescription;
    private String mineralName;
    private String typeMineralName;
    private String mineName;
    private String cooperativeName;

    private String state;

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
        this.state = load.getState().getValue();

        if (load.getSupplier() != null) {
            this.supplierId = load.getSupplier().getId();
            this.supplierName = load.getSupplier().getName()
                    + (load.getSupplier().getSurname() != null ? " " + load.getSupplier().getSurname() : "");
        }

        if (load.getLot() != null) {
            this.lotId = load.getLot().getId();
            this.lotDescription = load.getLot().getDescription();
        }

        if (load.getMineral() != null) {
            this.mineralId = load.getMineral().getId();
            this.mineralName = load.getMineral().getName();
        }


        if (load.getTypeMineral() != null) {
            this.typeMineralId = load.getTypeMineral().getId();
            this.typeMineralName = load.getTypeMineral().getName();
        }


        if (load.getMine() != null) {
            this.mineId = load.getMine().getId();
            this.mineName = load.getMine().getName();
        }

        if (load.getCooperative() != null) {
            this.cooperativeId = load.getCooperative().getId();
            this.cooperativeName = load.getCooperative().getName();
        }
    }
}