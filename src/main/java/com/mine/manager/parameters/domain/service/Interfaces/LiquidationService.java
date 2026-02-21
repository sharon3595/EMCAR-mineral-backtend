package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Liquidation;
import com.mine.manager.parameters.presentation.request.dto.LiquidationDto;
import com.mine.manager.parameters.presentation.request.dto.RoyaltyCalculationDto;
import com.mine.manager.parameters.presentation.request.filter.LiquidationFilter;
import com.mine.manager.parameters.presentation.response.pojo.LiquidationPojo;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import com.mine.manager.parameters.presentation.response.pojo.RoyaltyCalculationPojo;
import jakarta.validation.Valid;

import java.util.List;

public interface LiquidationService extends CRUDService<Liquidation, Integer> {

    LiquidationPojo update(Integer id, LiquidationDto dto);

    LiquidationPojo create(LiquidationDto dto);

    List<LiquidationPojo> getLiquidations();

    LiquidationPojo getLiquidationById(Integer id);

    void delete(Integer id);

    byte[] generateLiquidationPdf(Integer idLiquidation);

    List<LiquidationPojo> getFiltered(LiquidationFilter filter);

    PagePojo<LiquidationPojo> getByPageAndFilters(int page, int size, String sortBy,
                                                  String sortOrder, LiquidationFilter filter);

    RoyaltyCalculationPojo calculateRoyalties(@Valid RoyaltyCalculationDto request);
}

