package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.PercentageLiquidation;
import com.mine.manager.parameters.presentation.request.dto.LiquidPayableCalculationDto;
import com.mine.manager.parameters.presentation.request.dto.PercentageLiquidationDto;
import com.mine.manager.parameters.presentation.request.filter.LiquidationFilter;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import com.mine.manager.parameters.presentation.response.pojo.PercentageLiquidationPojo;

import java.math.BigDecimal;
import java.util.List;


public interface PercentageLiquidationService extends CRUDService<PercentageLiquidation, Integer> {


    BigDecimal calculatePayableAg(LiquidPayableCalculationDto dto);

    PercentageLiquidationPojo update(Integer id, PercentageLiquidationDto dto);


    PercentageLiquidationPojo create(PercentageLiquidationDto dto);


    List<PercentageLiquidationPojo> getPercentageLiquidations();


    PercentageLiquidationPojo getPercentageLiquidationById(Integer id);


    byte[] generatePercentageLiquidationPdf(Integer idPercentageLiquidation);


    List<PercentageLiquidationPojo> getFiltered(LiquidationFilter filter);


    PagePojo<PercentageLiquidationPojo> getByPageAndFilters(int page, int size, String sortBy,
                                                            String sortOrder, LiquidationFilter filter);
}