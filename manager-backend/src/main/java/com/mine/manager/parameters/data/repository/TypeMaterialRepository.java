package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.TypeMaterial;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeMaterialRepository extends GenericRepository<TypeMaterial, Integer> {
    Boolean existsByName(String name);
    Boolean existsByNameAndIdNot(String name, Integer id);
}

