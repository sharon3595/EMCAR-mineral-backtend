package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.Advance;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvanceRepository extends GenericRepository<Advance, Integer> {

    Optional<Advance> findByIdAndActiveTrue(Integer id);

    List<Advance> findAllByActiveIsTrue();

    List<Advance> findAllByLoadIdAndActiveIsTrue(Integer loadId);
}
