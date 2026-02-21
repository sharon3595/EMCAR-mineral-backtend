package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.TypeMineral;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeMineralRepository extends GenericRepository<TypeMineral, Integer> {
    Boolean existsByNameIgnoreCase(String name);

    Boolean existsByNameIgnoreCaseAndIdNot(String name, Integer id);

    @Query("""
            select tm from TypeMineral tm
            where tm.active = true
            and (:name is null or tm.name ilike :name)
            and (:symbol is null or tm.symbol ilike :symbol)
            and (
                :some is null or
                tm.name ilike :some or
                tm.symbol ilike :some
            )
            """)
    List<TypeMineral> searchByFilters(
            @Param("name") String name,
            @Param("symbol") String symbol,
            @Param("some") String some
    );
}

