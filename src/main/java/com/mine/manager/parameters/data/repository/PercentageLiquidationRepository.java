package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.PercentageLiquidation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;


public interface PercentageLiquidationRepository extends GenericRepository<PercentageLiquidation, Integer> {
    Optional<PercentageLiquidation> findByIdAndActiveTrue(Integer id);
    List<PercentageLiquidation> findAllByActiveIsTrue();


    boolean existsByLoadIdAndActiveTrue(Integer loadId);
    boolean existsByLoadIdAndIdNotAndActiveTrue(Integer loadId, Integer id);


    @Query("""
   SELECT lq.id
   FROM PercentageLiquidation lq
   JOIN lq.load l
   JOIN l.supplier s
   JOIN l.lot lo
   WHERE lq.active = true
   AND (
       LOWER(l.correlativeLotCode) LIKE LOWER(CONCAT('%', :some, '%'))
       OR LOWER(l.externalLot) LIKE LOWER(CONCAT('%', :some, '%'))
       OR LOWER(s.name) LIKE LOWER(CONCAT('%', :some, '%'))
       OR LOWER(s.surname) LIKE LOWER(CONCAT('%', :some, '%'))
       OR LOWER(lo.description) LIKE LOWER(CONCAT('%', :some, '%'))
   )
""")
    List<Integer> findIdsBySome(@Param("some") String some);
}
