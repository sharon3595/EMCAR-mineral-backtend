package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.Load;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoadRepository extends GenericRepository<Load, Integer> {

    Optional<Load> findByIdAndActiveTrue(Integer id);

    List<Load> findAllByActiveIsTrue();

    @Query("""
        SELECT l.id FROM Load l
        JOIN l.supplier s
        JOIN l.lot lo
        WHERE l.active = true
        AND (
            LOWER(l.correlativeLotCode) LIKE LOWER(CONCAT('%', :some, '%'))
            OR LOWER(l.externalLot) LIKE LOWER(CONCAT('%', :some, '%'))
            OR LOWER(s.name) LIKE LOWER(CONCAT('%', :some, '%'))
            OR LOWER(s.surname) LIKE LOWER(CONCAT('%', :some, '%'))
            OR LOWER(lo.description) LIKE LOWER(CONCAT('%', :some, '%'))
        )
    """)
    List<Integer> findIdsBySome(@Param("some") String some);

    boolean existsBySupplierId(Integer supplierId);

    boolean existsByMineralId(Integer mineralId);

    boolean existsByTypeMineralId(Integer typeMineralId);

    boolean existsByMineId(Integer mineId);

    boolean existsByCooperativeId(Integer cooperativeId);
}
