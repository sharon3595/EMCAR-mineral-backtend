package com.mine.manager.parameters.presentation.response.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mine.manager.common.Pojo;
import com.mine.manager.parameters.domain.entity.Supplier;
import com.mine.manager.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Pojo
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class SupplierPojo {

    private Integer id;
    private Boolean active;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String surname;
    private String fullName;
    private String documentNumber;
    private String address;
    private String expeditionPlace;

    public SupplierPojo(Supplier supplier) {
        this.id = supplier.getId();
        this.active = supplier.getActive();
        this.createdBy = supplier.getCreatedBy();
        this.updatedBy = supplier.getUpdatedBy();
        this.createdAt = supplier.getCreatedAt();
        this.updatedAt = supplier.getUpdatedAt();
        this.name = supplier.getName();
        this.surname = supplier.getSurname();
        this.fullName = StringUtil.concatenate(supplier.getName(),supplier.getSurname()," ").trim();
        this.documentNumber = supplier.getDocumentNumber();
        this.address = supplier.getAddress();
        this.expeditionPlace = supplier.getExpeditionPlace();
    }
}
