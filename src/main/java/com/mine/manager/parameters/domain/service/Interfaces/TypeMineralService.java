package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.TypeMineral;
import com.mine.manager.parameters.presentation.request.dto.TypeMineralDto;


import java.util.List;


public interface TypeMineralService extends CRUDService<TypeMineral, Integer> {
    TypeMineral create(TypeMineralDto dto);

    TypeMineral update(Integer id, TypeMineralDto dto);

    List<TypeMineral> getFilteredForSelect(String name, String symbol, String some);

    void delete(Integer id);
}
