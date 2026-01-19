package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.TypeMineral;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeMineralRepository extends GenericRepository<TypeMineral, Integer> {
    Boolean existsByName(String name);
    Boolean existsByNameAndIdNot(String name, Integer id);

    @Query("""
                SELECT t FROM TypeMineral t
                WHERE t.active = true
                AND (:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%')))
                AND (:symbol IS NULL OR LOWER(t.symbol) LIKE LOWER(CONCAT('%', :symbol, '%')))
                AND (:some IS NULL OR (
                    LOWER(t.name) LIKE LOWER(CONCAT('%', :some, '%'))
                    OR LOWER(t.symbol) LIKE LOWER(CONCAT('%', :some, '%'))
                ))
            """)
    List<TypeMaterial> searchByFilters(
            @Param("name") String name,
            @Param("symbol") String symbol,
            @Param("some") String some
    );
}

