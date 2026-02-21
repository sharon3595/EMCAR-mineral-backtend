package com.mine.manager.parameters.domain.mapper;

import com.mine.manager.parameters.domain.entity.Mine;
import com.mine.manager.parameters.presentation.request.dto.MineDto;
import org.springframework.stereotype.Component;

@Component
public class MineMapper {

    public Mine fromDto(MineDto dto, Mine found) {
        found = found != null ? found : new Mine();
        found.setName(dto.getName());
        found.setDescription(dto.getDescription());
        return found;
    }

    public Mine fromDto(MineDto dto) {
        return this.fromDto(dto,null);
    }
}
