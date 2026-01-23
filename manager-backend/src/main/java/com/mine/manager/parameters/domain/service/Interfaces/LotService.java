package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Lot;
import com.mine.manager.parameters.presentation.request.dto.LotDto;
import com.mine.manager.parameters.presentation.request.filter.LotFilter;
import com.mine.manager.parameters.presentation.response.pojo.LotPojo;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;

import java.util.List;

public interface LotService extends CRUDService<Lot, Integer> {

   LotPojo create(LotDto dto);

   LotPojo update(Integer id, LotDto dto);

   List<LotPojo> getLots();

   LotPojo getLotPojoById(Integer id);

   Lot getLotById(Integer id);

   void delete(Integer id);

    PagePojo<Lot> getByPageAndFilters(int page, int size, String sortBy, String sortOrder, LotFilter filter);

   Lot receiptLot();

   List<LotPojo> getFiltered(LotFilter filter);
}
