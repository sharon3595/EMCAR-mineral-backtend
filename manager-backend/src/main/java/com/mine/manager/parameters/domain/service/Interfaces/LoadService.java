package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Load;
import com.mine.manager.parameters.presentation.request.dto.LoadDto;
import com.mine.manager.parameters.presentation.request.filter.LoadFilter;
import com.mine.manager.parameters.presentation.response.pojo.CorrelativePojo;
import com.mine.manager.parameters.presentation.response.pojo.LoadPojo;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;

import java.util.List;

public interface LoadService extends  CRUDService<Load, Integer>{

    LoadPojo update(Integer id, LoadDto dto);

    LoadPojo create(LoadDto dto);

    List<LoadPojo> getLoads();

    LoadPojo getLoadById(Integer id);

    void delete(Integer id);

    CorrelativePojo processCorrelative(Integer lotId, boolean applyChange);

    List<LoadPojo> getFiltered(LoadFilter filter);

    PagePojo<LoadPojo> getByPageAndFilters(int page, int size, String sortBy, String sortOrder, LoadFilter filter);
}
