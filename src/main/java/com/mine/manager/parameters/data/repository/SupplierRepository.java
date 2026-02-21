package com.mine.manager.parameters.data.repository;

import com.mine.manager.parameters.domain.entity.Supplier;
import com.mine.manager.parameters.presentation.response.pojo.SupplierBasicInfoPojo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends GenericRepository<Supplier, Integer> {

    @Query("SELECT DISTINCT new com.mine.manager.parameters.presentation.response.pojo.SupplierBasicInfoPojo(" +
            "  s.id, " +
            "  s.documentNumber, " +
            "  s.name, " +
            "  s.surname, " +
            "  s.address, " +
            "  s.expeditionPlace" +
            ") " +
            "FROM Supplier s " +
            "WHERE :some IS NULL OR :some = '' OR " +
            "lower(concat(s.name, ' ', s.surname)) LIKE lower(concat('%', :some, '%')) OR " +
            "lower(s.documentNumber) LIKE lower(concat('%', :some, '%'))")
    List<SupplierBasicInfoPojo> getFilteredForSelect(@Param("some") String some);

    @Query("SELECT COUNT(s) > 0 FROM Supplier s WHERE s.name = :name AND " +
            "(:surname IS NULL OR s.surname = :surname)")
    boolean existsByNameAndOptionalSurname(@Param("name") String name, @Param("surname") String surname);

    boolean existsByDocumentNumber(String documentNumber);

    boolean existsByDocumentNumberAndIdNot(String documentNumber, Integer id);

    @Query("SELECT COUNT(s) > 0 FROM Supplier s WHERE " +
            "s.name = :name AND " +
            "(:surname IS NULL OR s.surname = :surname) AND " +
            "s.id != :id")
    boolean existsByNameAndOptionalSurnameForUpdate(
            @Param("name") String name,
            @Param("surname") String surname,
            @Param("id") Integer id
    );


    @Query("SELECT DISTINCT s.id FROM Supplier s " +
            "WHERE :some IS NULL OR :some = '' " +
            "OR lower(s.name) LIKE lower(concat('%', :some, '%')) " +
            "OR lower(COALESCE(s.surname, '')) LIKE lower(concat('%', :some, '%')) " +
            "OR lower(s.documentNumber) LIKE lower(concat('%', :some, '%')) " +
            "OR lower(COALESCE(s.address, '')) LIKE lower(concat('%', :some, '%'))")
    List<Integer> findIdsBySimilarFieldsIgnoreCase(@Param("some") String some);

}
