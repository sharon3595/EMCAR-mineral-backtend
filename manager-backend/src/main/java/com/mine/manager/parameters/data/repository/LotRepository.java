package com.mine.manager.parameters.data.repository;

import com.mine.manager.common.enums.LotTypeEnum;
import com.mine.manager.parameters.domain.entity.Lot;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotRepository extends GenericRepository<Lot, Integer> {

    boolean existsByPrefixAndInitialDocNumberAndActiveTrue(String prefix, Integer initialDocNumber);

    boolean existsByPrefixAndInitialDocNumberAndIdNotAndActiveTrue(String prefix, Integer initialDocNumber, Integer id);

    Optional<Lot> findByIdAndActiveTrue(Integer id);

    List<Lot> findAllByActiveIsTrue();

    Optional<Lot> findFirstByAssignmentAndStateTrueAndActiveTrue(LotTypeEnum assignment);
}
