package com.mine.manager.parameters.domain.mapper;

import com.mine.manager.parameters.domain.entity.Lot;
import com.mine.manager.parameters.presentation.response.pojo.LotPojo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LotMapper {

    public LotPojo toPojo(Lot lot) {
        return new LotPojo(lot);
    }

    public List<LotPojo> toPojoList(List<Lot> lots) {
        return lots.stream()
                .map(this::toPojo)
                .collect(Collectors.toList());
    }
}
