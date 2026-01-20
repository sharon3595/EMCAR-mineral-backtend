package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Mineral;
import com.mine.manager.parameters.presentation.request.dto.MineralDto;


import java.util.List;


public interface MineralService extends CRUDService<Mineral, Integer> {
    Mineral create(MineralDto dto);

    Mineral update(Integer id, MineralDto dto);

    List<Mineral> getFilteredForSelect(String name, String symbol, String some);

    void delete(Integer id);
}
