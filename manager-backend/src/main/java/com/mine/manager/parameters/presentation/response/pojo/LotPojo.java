package com.mine.manager.parameters.presentation.response.pojo;

import com.mine.manager.parameters.domain.entity.Lot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LotPojo {

    private Integer id;
    private Boolean active;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String prefix;
    private String description;
    private Integer initialDocNumber;
    private Integer currentDocNumber;
    private String correlative;
    private String assignment;
    private Boolean state;

    public LotPojo(Lot lot) {

        this.id = lot.getId();
        this.active = lot.getActive();
        this.createdBy = lot.getCreatedBy();
        this.updatedBy = lot.getUpdatedBy();
        this.createdAt = lot.getCreatedAt();
        this.updatedAt = lot.getUpdatedAt();

        this.prefix = lot.getPrefix();
        this.description = lot.getDescription();
        this.initialDocNumber = lot.getInitialDocNumber();
        this.currentDocNumber = lot.getCurrentDocNumber();
        this.correlative = lot.getPrefix() + (
                lot.getCurrentDocNumber() != null
                        ? lot.getCurrentDocNumber() + 1
                        : lot.getInitialDocNumber()
        );
        this.assignment = lot.getAssignment().getValue();
        this.state = lot.getState();
    }
}
