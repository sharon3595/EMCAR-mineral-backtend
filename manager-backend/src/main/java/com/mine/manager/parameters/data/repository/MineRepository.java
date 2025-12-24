package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.Mine;
import org.springframework.stereotype.Repository;

@Repository
public interface MineRepository extends GenericRepository<Mine, Integer> {
    Boolean existsByName(String name);
    Boolean existsByNameAndIdNot(String name, Integer id);
}