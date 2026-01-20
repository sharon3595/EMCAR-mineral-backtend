package com.mine.manager.parameters.domain.mapper;

import com.mine.manager.parameters.domain.entity.TypeMineral;
import com.mine.manager.parameters.presentation.request.dto.TypeMineralDto;
import org.springframework.stereotype.Component;

@Component
public class TypeMineralMapper {


    public TypeMineral fromDto(TypeMineralDto dto, TypeMineral found) {
        found = found != null ? found : new TypeMineral();
        found.setName(dto.getName());
        found.setSymbol(dto.getSymbol());
        return found;
    }

    public TypeMineral fromDto(TypeMineralDto dto) {
        return this.fromDto(dto, null);
    }
}
