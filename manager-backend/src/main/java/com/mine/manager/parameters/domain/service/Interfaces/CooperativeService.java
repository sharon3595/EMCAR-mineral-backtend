package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Cooperative;
import com.mine.manager.parameters.presentation.request.dto.CooperativeDto;

public interface CooperativeService extends CRUDService<Cooperative, Integer> {
    Cooperative create(CooperativeDto dto);

    Cooperative update(Integer id, CooperativeDto dto);

    void delete(Integer id);
}