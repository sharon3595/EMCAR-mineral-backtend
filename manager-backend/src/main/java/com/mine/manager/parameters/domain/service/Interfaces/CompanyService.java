package com.mine.manager.parameters.domain.service.Interfaces;

import com.mine.manager.parameters.domain.entity.Company;
import com.mine.manager.parameters.presentation.request.dto.CompanyDto;

import java.util.List;

public interface CompanyService extends CRUDService<Company, Integer>{


    Company create(CompanyDto dto);
    Company update(Integer id, CompanyDto dto);

}
