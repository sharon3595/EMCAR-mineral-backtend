package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.Company;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends GenericRepository<Company, Integer>{
}