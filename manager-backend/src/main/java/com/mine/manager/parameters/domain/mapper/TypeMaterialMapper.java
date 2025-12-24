package com.mine.manager.parameters.domain.mapper;

import com.mine.manager.parameters.domain.entity.TypeMaterial;
import com.mine.manager.parameters.presentation.request.dto.TypeMaterialDto;
import org.springframework.stereotype.Component;


@Component
public class TypeMaterialMapper {


    public TypeMaterial fromDto(TypeMaterialDto dto, TypeMaterial found) {
        found = found != null ? found : new TypeMaterial();
        found.setName(dto.getName());
        found.setDescription(dto.getDescription());
        return found;
    }


    public TypeMaterial fromDto(TypeMaterialDto dto) {
        return this.fromDto(dto,null);
    }
}
