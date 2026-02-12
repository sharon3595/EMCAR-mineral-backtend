package com.mine.manager.parameters.presentation.response.pojo;


import com.mine.manager.parameters.domain.entity.PercentageLiquidation;
import com.mine.manager.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PercentageLiquidationPojo {

    private Integer id;
    private Boolean active;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer loadId;
    private Integer supplierId;
    private Integer lotId;
    private String externalLot;
    private String correlativeLotCode;
    private String supplierName;

    private BigDecimal cajaNacional;
    private BigDecimal fedecomin;
    private BigDecimal fencomin;
    private BigDecimal comibol;
    private BigDecimal miningRoyalties;

    private LocalDate admissionDate;
    private LocalDate percentageLiquidationDate;

    private BigDecimal exchangeRate;

    private BigDecimal quotation;
    private BigDecimal percentage;

    private BigDecimal dmSilver;

    private BigDecimal humidityPercentage;
    private BigDecimal metricWetKilograms;
    private BigDecimal dryMetricKilograms;

    private BigDecimal firstAdvance;

    private BigDecimal transportation;

    private BigDecimal amountPayableBc;
    private BigDecimal amountPayableTc;

    private BigDecimal totalPrice;

    private BigDecimal discounts;

    public PercentageLiquidationPojo(PercentageLiquidation liquidation) {

        this.id = liquidation.getId();
        this.active = liquidation.getActive();
        this.createdBy = liquidation.getCreatedBy();
        this.updatedBy = liquidation.getUpdatedBy();
        this.createdAt = liquidation.getCreatedAt();
        this.updatedAt = liquidation.getUpdatedAt();

        if (liquidation.getLoad() != null) {
            var load = liquidation.getLoad();
            this.loadId = load.getId();
            this.externalLot = load.getExternalLot();
            this.correlativeLotCode = load.getCorrelativeLotCode();

            if (load.getSupplier() != null) {
                this.supplierId = load.getSupplier().getId();
                this.supplierName = StringUtil.concatenate
                        (load.getSupplier().getName(), load.getSupplier().getSurname(), " ");
            }

            if (load.getLot() != null) {
                this.lotId = load.getLot().getId();
            }

        }


        this.cajaNacional = liquidation.getCajaNacional();
        this.fedecomin = liquidation.getFedecomin();
        this.fencomin = liquidation.getFencomin();
        this.comibol = liquidation.getComibol();
        this.miningRoyalties = liquidation.getMiningRoyalties();


        this.admissionDate = liquidation.getAdmissionDate();
        this.percentageLiquidationDate = liquidation.getPercentageLiquidationDate();


        this.exchangeRate = liquidation.getExchangeRate();

        this.quotation = liquidation.getQuotation();
        this.percentage = liquidation.getPercentage();
        this.dmSilver = liquidation.getDmSilver();

        // Pesos
        this.humidityPercentage = liquidation.getHumidityPercentage();
        this.metricWetKilograms = liquidation.getMetricWetKilograms();
        this.dryMetricKilograms = liquidation.getDryMetricKilograms();


        // Anticipos y Bonos
        this.firstAdvance = liquidation.getFirstAdvance();

        // Totales Finales
        this.amountPayableBc = liquidation.getAmountPayableBc();
        this.amountPayableTc = liquidation.getAmountPayableTc();
        this.totalPrice = liquidation.getTotalPrice();
        this.discounts = liquidation.getDiscounts();
    }
}
