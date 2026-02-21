package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.Cooperative;
import org.springframework.stereotype.Repository;

@Repository
public interface CooperativeRepository extends GenericRepository<Cooperative, Integer> {
    Boolean existsByName(String name);
    Boolean existsByNameAndIdNot(String name, Integer id);
}
