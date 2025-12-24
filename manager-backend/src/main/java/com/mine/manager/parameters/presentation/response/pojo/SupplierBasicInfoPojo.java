package com.mine.manager.parameters.presentation.response.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.mine.manager.common.Pojo;
import com.mine.manager.parameters.domain.entity.Supplier;
import com.mine.manager.util.StringUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Pojo
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplierBasicInfoPojo {


    private Integer id;
    private String documentNumber;
    private String fullName;
    private String address;
    private String expeditionPlace;


    public SupplierBasicInfoPojo(Supplier supplier) {
        this(supplier.getId(), supplier.getDocumentNumber(), supplier.getName(), supplier.getSurname(),
                supplier.getAddress(), supplier.getExpeditionPlace());
    }


    public SupplierBasicInfoPojo(Integer supplierId, String supplierDocumentNumber, String supplierName, String supplierSurname, String address, String expeditionPlace) {
        this.id = supplierId;
        this.documentNumber = supplierDocumentNumber;
        this.fullName = StringUtil.concatenate(supplierName, supplierSurname, " ");
        this.address = address;
        this.expeditionPlace = expeditionPlace;
    }

}
