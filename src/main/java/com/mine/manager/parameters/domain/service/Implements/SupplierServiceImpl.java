package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpecificationUtils;
import com.mine.manager.common.enums.sortByPageable.SupplierPojoEnum;
import com.mine.manager.exception.DuplicateException;
import com.mine.manager.exception.HasAsociatedEntityException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.LoadRepository;
import com.mine.manager.parameters.data.repository.SupplierRepository;
import com.mine.manager.parameters.domain.entity.Supplier;
import com.mine.manager.parameters.domain.mapper.SupplierMapper;
import com.mine.manager.parameters.domain.service.Interfaces.SupplierService;
import com.mine.manager.parameters.presentation.request.dto.SupplierDto;
import com.mine.manager.parameters.presentation.request.filter.SupplierFilter;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import com.mine.manager.parameters.presentation.response.pojo.SupplierBasicInfoPojo;
import com.mine.manager.parameters.presentation.response.pojo.SupplierPojo;
import com.mine.manager.util.FieldsFilterUtil;
import com.mine.manager.util.StringUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class SupplierServiceImpl extends CRUDServiceImpl<Supplier, Integer> implements SupplierService {


    private final SupplierRepository supplierRepository;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;
    private final SupplierMapper supplierMapper;
    private static final String SUPPLIER = "Cliente";
    private final LoadRepository loadRepository;


    @Override
    protected GenericRepository<Supplier, Integer> getRepository() {
        return supplierRepository;
    }


    @Override
    public SupplierPojo create(SupplierDto dto) {
        Supplier supplier = convertToEntity(dto);
        if (supplierRepository.existsByDocumentNumber(dto.getDocumentNumber())) {
            throw new DuplicateException(SUPPLIER, "documento", dto.getDocumentNumber());
        }
        if (supplierRepository.existsByNameAndOptionalSurname(dto.getName(), dto.getSurname())) {
            throw new DuplicateException(SUPPLIER, "nombre", getSupplierFullName(dto.getName(), dto.getSurname()));
        }
        return new SupplierPojo(supplierRepository.save(supplier));
    }

    private String getSupplierFullName(String name, String surname) {
        return StringUtil.concatenate(name, surname, " ").trim();
    }

    @Override
    public SupplierPojo update(Integer id, SupplierDto dto) {
        Supplier supplierFound = this.getById(id);
        if (supplierRepository.existsByDocumentNumberAndIdNot(dto.getDocumentNumber(), id)) {
            throw new DuplicateException(SUPPLIER, "documento", dto.getDocumentNumber());
        }
        if (supplierRepository.existsByNameAndOptionalSurnameForUpdate(dto.getName(), dto.getSurname(), id)) {
            throw new DuplicateException(SUPPLIER, "nombre", getSupplierFullName(dto.getName(), dto.getSurname()));
        }
        Supplier supplier = convertToEntity(dto);
        supplier.setId(supplierFound.getId());
        supplier.setCreatedAt(supplierFound.getCreatedAt());
        Supplier updated = supplierRepository.save(supplier);
        return new SupplierPojo(updated);
    }


    @Override
    public PagePojo<SupplierPojo> getByPageAndFilters(int page, int size, String sortBy,
                                                      String sortOrder, SupplierFilter filter) {
        String validSortBy = SupplierPojoEnum.fromString(sortBy);
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), validSortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Supplier> spec = this.generateSpecification(filter);
        Page<Supplier> filtered = supplierRepository.findAll(spec, pageable);
        return supplierMapper.fromPageToPagePojo(filtered);
    }


    @Override
    public List<Supplier> getFiltered(SupplierFilter filter) {
        Specification<Supplier> spec = this.generateSpecification(filter);
        return supplierRepository.findAll(spec);
    }


    @Override
    public List<SupplierBasicInfoPojo> getFilteredForSelect(String some) {
        return supplierRepository.getFilteredForSelect(StringUtil.removeExtraSpaces(some));
    }

    private Specification<Supplier> generateSpecification(SupplierFilter filter) {
        FieldsFilterUtil fields = new FieldsFilterUtil();
        fields.addLikeField("documentNumber", filter.getDocumentNumber());
        fields.addLikeField("address", filter.getAddress());
        fields.addLikeField("name", filter.getName());
        fields.addLikeField("surname", filter.getSurname());
        List<Integer> someIds = supplierRepository.findIdsBySimilarFieldsIgnoreCase(filter.getSome());
        fields.addInSomeField("id", someIds);
        return SpecificationUtils.createSpecification(fields.getFilterFields());
    }


    private Supplier convertToEntity(SupplierDto dto) {
        return mapper.map(dto, Supplier.class);
    }

    @Override
    public void delete(Integer id) {
        Supplier toDelete = this.getById(id);
        if (loadRepository.existsBySupplierId(id)) {
            throw new HasAsociatedEntityException(
                    SUPPLIER, "Cargas"
            );
        }
        supplierRepository.delete(toDelete);
    }
}



