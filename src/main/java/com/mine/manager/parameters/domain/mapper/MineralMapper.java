package com.mine.manager.parameters.domain.mapper;

import com.mine.manager.parameters.domain.entity.Mineral;
import com.mine.manager.parameters.presentation.request.dto.MineralDto;
import org.springframework.stereotype.Component;


@Component
public class MineralMapper {


    public Mineral fromDto(MineralDto dto, Mineral found) {
        found = found != null ? found : new Mineral();
        found.setName(dto.getName());
        found.setSymbol(dto.getSymbol());
        return found;
    }


    public Mineral fromDto(MineralDto dto) {
        return this.fromDto(dto, null);}
}
