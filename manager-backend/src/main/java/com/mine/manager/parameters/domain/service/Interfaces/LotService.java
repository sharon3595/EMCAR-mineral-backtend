package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Lot;
import com.mine.manager.parameters.presentation.request.dto.LotDto;

import java.util.List;

public interface LotService extends CRUDService<Lot, Integer> {

   Lot create(LotDto dto);

   Lot update(Integer id, LotDto dto);

   List<Lot> getLots();

   Lot getLotById(Integer id);

   void delete(Integer id);

}
