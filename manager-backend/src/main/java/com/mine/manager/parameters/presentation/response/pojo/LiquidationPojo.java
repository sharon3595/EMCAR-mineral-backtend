package com.mine.manager.parameters.presentation.response.pojo;

import com.mine.manager.parameters.domain.entity.Liquidation;
import com.mine.manager.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LiquidationPojo {

    private Integer id;
    private Boolean active;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String liquidationType;
    private Integer loadId;
    private Integer supplierId;
    private Integer lotId;
    private Integer mineralId;
    private Integer mineId;
    private Integer cooperativeId;
    private String externalLot;
    private String correlativeLotCode;
    private String supplierName;
    private String mineralName;
    private String mineName;
    private String cooperativeName;

    private BigDecimal cajaNacional;
    private BigDecimal fedecomin;
    private BigDecimal fencomin;
    private BigDecimal comibol;
    private BigDecimal wilstermann;
    private BigDecimal cooperativeContribution;
    private BigDecimal miningRoyalties;

    private LocalDate admissionDate;
    private LocalDate liquidationDate;

    private BigDecimal exchangeRate;

    private BigDecimal priceSilver;
    private BigDecimal priceZinc;
    private BigDecimal priceLead;

    private BigDecimal lawSilver;
    private BigDecimal lawZinc;
    private BigDecimal lawLead;

    private BigDecimal humidityPercentage;
    private BigDecimal metricWetKilograms;
    private BigDecimal dryMetricKilograms;

    private BigDecimal quotationSilver;
    private BigDecimal quotationZinc;
    private BigDecimal quotationLead;

    private BigDecimal royaltySilver;
    private BigDecimal royaltyZinc;
    private BigDecimal royaltyLead;

    private BigDecimal firstAdvance;
    private BigDecimal secondAdvance;

    private BigDecimal transportationBonus;

    private BigDecimal amountPayableBc;
    private BigDecimal amountPayableTc;

    private BigDecimal totalPrice;

    public LiquidationPojo(Liquidation liquidation) {

        this.id = liquidation.getId();
        this.active = liquidation.getActive();
        this.createdBy = liquidation.getCreatedBy();
        this.updatedBy = liquidation.getUpdatedBy();
        this.createdAt = liquidation.getCreatedAt();
        this.updatedAt = liquidation.getUpdatedAt();

        if (liquidation.getLiquidationTypeEnum() != null) {
            this.liquidationType = liquidation.getLiquidationTypeEnum().getValue();
        }

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

            if (load.getMineral() != null) {
                this.mineralId = load.getMineral().getId();
                this.mineralName = load.getMineral().getName();
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


        this.cajaNacional = liquidation.getCajaNacional();
        this.fedecomin = liquidation.getFedecomin();
        this.fencomin = liquidation.getFencomin();
        this.comibol = liquidation.getComibol();
        this.cooperativeContribution = liquidation.getCooperativeContribution();
        this.miningRoyalties = liquidation.getMiningRoyalties();


        this.admissionDate = liquidation.getAdmissionDate();
        this.liquidationDate = liquidation.getLiquidationDate();


        this.exchangeRate = liquidation.getExchangeRate();

        // Precios
        this.priceSilver = liquidation.getPriceSilver();
        this.priceZinc = liquidation.getPriceZinc();
        this.priceLead = liquidation.getPriceLead();

        // Leyes
        this.lawSilver = liquidation.getLawSilver();
        this.lawZinc = liquidation.getLawZinc();
        this.lawLead = liquidation.getLawLead();

        // Pesos
        this.humidityPercentage = liquidation.getHumidityPercentage();
        this.metricWetKilograms = liquidation.getMetricWetKilograms();
        this.dryMetricKilograms = liquidation.getDryMetricKilograms();

        // Cotizaciones
        this.quotationSilver = liquidation.getQuotationSilver();
        this.quotationZinc = liquidation.getQuotationZinc();
        this.quotationLead = liquidation.getQuotationLead();

        // Regalías Calculadas
        this.royaltySilver = liquidation.getRoyaltySilver();
        this.royaltyZinc = liquidation.getRoyaltyZinc();
        this.royaltyLead = liquidation.getRoyaltyLead();

        // Anticipos y Bonos
        this.firstAdvance = liquidation.getFirstAdvance();
        this.secondAdvance = liquidation.getSecondAdvance();
        this.transportationBonus = liquidation.getTransportationBonus();

        // Totales Finales
        this.amountPayableBc = liquidation.getAmountPayableBc();
        this.amountPayableTc = liquidation.getAmountPayableTc();
        this.totalPrice = liquidation.getTotalPrice();
    }
}
