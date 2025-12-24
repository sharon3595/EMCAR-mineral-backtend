package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Mine;
import com.mine.manager.parameters.presentation.request.dto.MineDto;

public interface MineService extends CRUDService<Mine, Integer> {
    Mine create(MineDto dto);
    Mine update(Integer id, MineDto dto);
}