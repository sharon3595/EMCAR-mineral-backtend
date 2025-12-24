package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.Material;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends GenericRepository<Material, Integer> {
    Boolean existsByName(String name);
    Boolean existsByNameAndIdNot(String name, Integer id);
}
