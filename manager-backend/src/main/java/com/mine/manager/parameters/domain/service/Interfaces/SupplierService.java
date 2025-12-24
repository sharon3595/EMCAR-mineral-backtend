package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Supplier;
import com.mine.manager.parameters.presentation.request.dto.SupplierDto;
import com.mine.manager.parameters.presentation.request.filter.SupplierFilter;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import com.mine.manager.parameters.presentation.response.pojo.SupplierBasicInfoPojo;
import com.mine.manager.parameters.presentation.response.pojo.SupplierPojo;

import java.util.List;

public interface SupplierService extends CRUDService<Supplier, Integer>{
    SupplierPojo create(SupplierDto dto);

    SupplierPojo update(Integer id, SupplierDto dto);

    PagePojo<SupplierPojo> getByPageAndFilters(int page, int size, String sortBy,
                                               String sortOrder, SupplierFilter filter);

    List<Supplier> getFiltered(SupplierFilter filter);

    List<SupplierBasicInfoPojo> getFilteredForSelect(String some);
}
