package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Advance;
import com.mine.manager.parameters.presentation.request.dto.AdvanceDto;
import com.mine.manager.parameters.presentation.response.pojo.AdvancePojo;

import java.util.List;

public interface AdvanceService extends CRUDService<Advance, Integer> {

    AdvancePojo update(Integer id, AdvanceDto dto);

    AdvancePojo create(AdvanceDto dto);

    List<AdvancePojo> getAdvances();

    AdvancePojo getAdvanceById(Integer id);

    void delete(Integer id);

    List<AdvancePojo> getAdvancesByLoadId(Integer id);
}
