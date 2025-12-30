package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.Mine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MineRepository extends GenericRepository<Mine, Integer> {
    Boolean existsByName(String name);

    Boolean existsByNameAndIdNot(String name, Integer id);

    @Query("""
                SELECT m FROM Mine m
                WHERE m.active = true
                AND (:name IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%')))
                AND (:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))
                AND (:some IS NULL OR (
                    LOWER(m.name) LIKE LOWER(CONCAT('%', :some, '%'))
                    OR LOWER(m.description) LIKE LOWER(CONCAT('%', :some, '%'))
                ))
            """)
    List<Mine> searchByFilters(
            @Param("name") String name,
            @Param("description") String description,
            @Param("some") String some
    );
}