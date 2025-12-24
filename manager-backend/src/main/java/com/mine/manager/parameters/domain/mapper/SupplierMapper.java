package com.mine.manager.parameters.domain.mapper;


import com.mine.manager.parameters.domain.entity.Supplier;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import com.mine.manager.parameters.presentation.response.pojo.SupplierPojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupplierMapper {


    public PagePojo<SupplierPojo> fromPageToPagePojo(Page<Supplier> page){
        PagePojo<SupplierPojo> dto = new PagePojo<>();
        dto.setContent(SupplierMapper.toPojoList(page.getContent()));
        dto.setLast(page.isLast());
        dto.setPageNumber(page.getNumber());
        dto.setPageSize(page.getSize());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        return dto;
    }
    public static SupplierPojo toPojo(Supplier supplier){
        return new SupplierPojo(supplier);
    }
    public static List<SupplierPojo> toPojoList(List<Supplier> people) {
        return people.stream()
                .map(SupplierMapper::toPojo)
                .collect(Collectors.toList());
    }
}

