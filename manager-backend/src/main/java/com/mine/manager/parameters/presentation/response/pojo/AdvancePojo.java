package com.mine.manager.parameters.presentation.response.pojo;

import com.mine.manager.common.Pojo;
import com.mine.manager.parameters.domain.entity.Advance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Pojo
@Getter
@Setter
@NoArgsConstructor
public class AdvancePojo {

    private Integer id;
    private Boolean active;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String receiptType;
    private String receiptCode;
    private LocalDate date;
    private BigDecimal amount;
    private String concept;
    private String observation;
    private String paymentType;
    private String checkNumber;
    private String paymentChanel;
    private Integer loadId;

    public AdvancePojo(Advance advance) {
        this.id = advance.getId();
        this.active = advance.getActive();
        this.createdBy = advance.getCreatedBy();
        this.updatedBy = advance.getUpdatedBy();
        this.createdAt = advance.getCreatedAt();
        this.updatedAt = advance.getUpdatedAt();
        this.receiptType = advance.getReceiptType().getValue();
        this.receiptCode = advance.getReceiptCode();
        this.amount = advance.getAmount();
        this.concept = advance.getConcept();
        this.observation = advance.getObservation();
        this.paymentType = advance.getPaymentType().getValue();
        this.checkNumber = advance.getCheckNumber();
        this.paymentChanel = advance.getPaymentChanel().getValue();
        this.loadId = advance.getLoad().getId();
    }
}
