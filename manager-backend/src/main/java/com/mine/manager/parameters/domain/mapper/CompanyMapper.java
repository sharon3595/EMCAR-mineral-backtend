package com.mine.manager.parameters.domain.mapper;


import com.mine.manager.parameters.domain.entity.Company;
import com.mine.manager.parameters.presentation.request.dto.CompanyDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CompanyMapper {

    public Company fromDto(CompanyDto dto){
        Company company = new Company();
        company.setCode(dto.getCode());
        company.setSocialReason(dto.getSocialReason());
        company.setNit(dto.getNit());
        company.setPurpose(dto.getPurpose());
        company.setLogo(dto.getLogo());
        company.setNim(dto.getNim());
        company.setAddress(dto.getAddress());
        company.setCellphone(dto.getCellphone());
        return  company;
    }

    public Company updateFromDto(Company company, CompanyDto dto){
        company.setCode(dto.getCode());
        company.setSocialReason(dto.getSocialReason());
        company.setNit(dto.getNit());
        company.setPurpose(dto.getPurpose());
        company.setLogo(dto.getLogo());
        company.setNim(dto.getNim());
        company.setAddress(dto.getAddress());
        company.setCellphone(dto.getCellphone());
        return company;
    }
}