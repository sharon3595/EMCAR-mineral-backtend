package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.TypeMaterial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeMaterialRepository extends GenericRepository<TypeMaterial, Integer> {
    Boolean existsByName(String name);
    Boolean existsByNameAndIdNot(String name, Integer id);

    @Query("""
                SELECT t FROM TypeMaterial t
                WHERE t.active = true
                AND (:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%')))
                AND (:description IS NULL OR LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%')))
                AND (:some IS NULL OR (
                    LOWER(t.name) LIKE LOWER(CONCAT('%', :some, '%'))
                    OR LOWER(t.description) LIKE LOWER(CONCAT('%', :some, '%'))
                ))
            """)
    List<TypeMaterial> searchByFilters(
            @Param("name") String name,
            @Param("description") String description,
            @Param("some") String some
    );
}

