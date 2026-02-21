package com.mine.manager.parameters.domain.mapper;

import com.mine.manager.parameters.domain.entity.Load;
import com.mine.manager.parameters.presentation.request.dto.LoadDto;
import com.mine.manager.parameters.presentation.response.pojo.LoadPojo;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LoadMapper {

    public Load fromDto(LoadDto dto, Load load) {
        load.setDate(dto.getDate());
        load.setCorrelativeLotCode(dto.getCorrelativeLotCode());
        load.setNumberSacks(dto.getNumberSacks());
        load.setWeight(dto.getWeight());
        load.setObservation(dto.getObservation());

        return load;
    }

    public LoadPojo toPojo(Load load) {
        return new LoadPojo(load);
    }

    public List<LoadPojo> toPojoList(List<Load> loads) {
        return loads.stream()
                .map(this::toPojo)
                .collect(Collectors.toList());
    }

    public PagePojo<LoadPojo> fromPageToPagePojo(Page<Load> page){
        PagePojo<LoadPojo> dto = new PagePojo<>();
        dto.setContent(toPojoList(page.getContent()));
        dto.setLast(page.isLast());
        dto.setPageNumber(page.getNumber());
        dto.setPageSize(page.getSize());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        return dto;
    }
}