package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.Mineral;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MineralRepository extends GenericRepository<Mineral, Integer> {
    Boolean existsBySymbol(String symbol);
    Boolean existsBySymbolAndIdNot(String symbol, Integer id);

    @Query("""
                SELECT m FROM Mineral m
                WHERE m.active = true
                AND (:name IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%')))
                AND (:symbol IS NULL OR LOWER(m.symbol) LIKE LOWER(CONCAT('%', :symbol, '%')))
                AND (:some IS NULL OR (
                    LOWER(m.name) LIKE LOWER(CONCAT('%', :some, '%'))
                    OR LOWER(m.symbol) LIKE LOWER(CONCAT('%', :some, '%'))
                ))
            """)
    List<Mineral> searchByFilters(
            @Param("name") String name,
            @Param("symbol") String symbol,
            @Param("some") String some
    );
}
