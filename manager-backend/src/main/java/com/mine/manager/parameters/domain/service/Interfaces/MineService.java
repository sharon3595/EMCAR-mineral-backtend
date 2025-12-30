package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Mine;
import com.mine.manager.parameters.presentation.request.dto.MineDto;

import java.util.List;

public interface MineService extends CRUDService<Mine, Integer> {
    Mine create(MineDto dto);
    Mine update(Integer id, MineDto dto);

    List<Mine> getFilteredForSelect(String name, String description, String some);
}