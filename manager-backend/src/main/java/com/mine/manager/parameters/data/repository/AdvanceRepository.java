package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.Advance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdvanceRepository extends GenericRepository<Advance, Integer> {

    Optional<Advance> findByIdAndActiveTrue(Integer id);

    List<Advance> findAllByActiveIsTrue();

    List<Advance> findAllByLoadIdAndActiveIsTrue(Integer loadId);

    @Query("""
        SELECT COALESCE(SUM(a.amount), 0)
        FROM Advance a
        WHERE a.load.id = :loadId
        AND a.active = true
    """)
    BigDecimal sumAmountByLoadId(@Param("loadId") Integer loadId);
}
