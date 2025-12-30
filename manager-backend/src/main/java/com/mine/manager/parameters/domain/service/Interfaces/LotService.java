package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Lot;
import com.mine.manager.parameters.presentation.request.dto.LotDto;
import com.mine.manager.parameters.presentation.request.filter.LotFilter;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;

import java.util.List;

public interface LotService extends CRUDService<Lot, Integer> {

   Lot create(LotDto dto);

   Lot update(Integer id, LotDto dto);

   List<Lot> getLots();

   Lot getLotById(Integer id);

   void delete(Integer id);

    PagePojo<Lot> getByPageAndFilters(int page, int size, String sortBy, String sortOrder, LotFilter filter);

   List<Lot> getFiltered(LotFilter filter);
}
