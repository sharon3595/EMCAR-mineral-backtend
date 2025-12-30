package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Material;
import com.mine.manager.parameters.presentation.request.dto.MaterialDto;

import java.util.List;

public interface MaterialService extends CRUDService<Material, Integer> {
    Material create(MaterialDto dto);
    Material update(Integer id, MaterialDto dto);

    List<Material> getFilteredForSelect(String name, String description, String some);
}