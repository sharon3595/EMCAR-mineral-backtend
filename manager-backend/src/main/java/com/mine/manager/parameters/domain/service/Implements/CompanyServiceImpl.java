package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.exception.EntityNotFoundException;
import com.mine.manager.parameters.data.repository.CompanyRepository;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.domain.entity.Company;
import com.mine.manager.parameters.domain.mapper.CompanyMapper;
import com.mine.manager.parameters.domain.service.Interfaces.CompanyService;
import com.mine.manager.parameters.presentation.request.dto.CompanyDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl extends CRUDServiceImpl<Company, Integer> implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private static final String COMPANY = SpanishEntityNameProvider.getSpanishName(Company.class.getSimpleName());

    @Override
    protected GenericRepository<Company, Integer> getRepository() {
        return companyRepository;
    }

    @Override
    public Company create(CompanyDto dto) {
        return companyRepository.save(companyMapper.fromDto(dto));
    }

    @Override
    public Company update(Integer id, CompanyDto dto) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            return companyRepository.save(companyMapper.updateFromDto(company.get(), dto));
        }
        throw new EntityNotFoundException(COMPANY, id);
    }
}

