package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.TypeMaterial;
import com.mine.manager.parameters.presentation.request.dto.TypeMaterialDto;

import java.util.List;

public interface TypeMaterialService extends CRUDService<TypeMaterial, Integer> {
    TypeMaterial create(TypeMaterialDto dto);
    TypeMaterial update(Integer id, TypeMaterialDto dto);

    List<TypeMaterial> getFilteredForSelect(String name, String description, String some);
}