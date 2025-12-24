package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.TypeMaterial;
import com.mine.manager.parameters.presentation.request.dto.TypeMaterialDto;

public interface TypeMaterialService extends CRUDService<TypeMaterial, Integer> {
    TypeMaterial create(TypeMaterialDto dto);
    TypeMaterial update(Integer id, TypeMaterialDto dto);
}