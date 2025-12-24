package com.mine.manager.parameters.domain.mapper;

import com.mine.manager.parameters.domain.entity.Material;
import com.mine.manager.parameters.presentation.request.dto.MaterialDto;
import org.springframework.stereotype.Component;


@Component
public class MaterialMapper {


    public Material fromDto(MaterialDto dto, Material found) {
        found = found != null ? found : new Material();
        found.setName(dto.getName());
        found.setDescription(dto.getDescription());
        return found;
    }


    public Material fromDto(MaterialDto dto) {
        return this.fromDto(dto, null);
    }
}
