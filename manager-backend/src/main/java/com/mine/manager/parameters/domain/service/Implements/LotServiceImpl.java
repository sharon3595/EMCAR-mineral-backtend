package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.common.SpecificationUtils;
import com.mine.manager.exception.DuplicateException;
import com.mine.manager.exception.EntityNotFoundException;
import com.mine.manager.exception.HasAsociatedEntityException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.LotRepository;
import com.mine.manager.parameters.domain.entity.Lot;
import com.mine.manager.parameters.domain.service.Interfaces.LotService;
import com.mine.manager.parameters.presentation.request.dto.LotDto;
import com.mine.manager.parameters.presentation.request.filter.LotFilter;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
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
public class LotServiceImpl extends CRUDServiceImpl<Lot, Integer> implements
        LotService {


    private final LotRepository lotRepository;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;
    private static final String LOT = SpanishEntityNameProvider.getSpanishName(Lot.class.getSimpleName());


    @Override
    protected GenericRepository<Lot, Integer> getRepository() {
        return lotRepository;
    }


    @Override
    public Lot create(LotDto dto) {
        if (lotRepository.existsByPrefixAndInitialDocNumberAndActiveTrue(dto.getPrefix(), dto.getInitialDocNumber())) {
            throw new DuplicateException(LOT, "prefijo y numero inicial", StringUtil.concatenate(dto.getPrefix(),
                    dto.getInitialDocNumber().toString(), " "));
        }
        Lot lot = convertToEntity(dto);
        return lotRepository.save(lot);
    }


    @Override
    public Lot update(Integer id, LotDto dto) {
        Lot lotFound = this.getById(id);
        if (lotRepository.existsByPrefixAndInitialDocNumberAndIdNotAndActiveTrue(dto.getPrefix(), dto.getInitialDocNumber(),id)) {
            throw new DuplicateException(LOT, "prefijo y numero inicial", StringUtil.concatenate(dto.getPrefix(),
                    dto.getInitialDocNumber().toString(), " "));
        }
        Lot updatedLot = updateLotEntity(lotFound, dto);
        return lotRepository.save(updatedLot);
    }

    private Lot updateLotEntity(Lot lotFound, LotDto dto) {
        Lot lot = convertToEntity(dto);
        lot.setId(lotFound.getId());
        lot.setCurrentDocNumber(lotFound.getCurrentDocNumber());
        if (dto.getInitialDocNumber()!=null && lotFound.getCurrentDocNumber()==null)
        {lot.setInitialDocNumber(dto.getInitialDocNumber());}
        else{lot.setInitialDocNumber(lotFound.getInitialDocNumber());}
        return lot;
    }

    @Override
    public List<Lot> getLots() {
        return lotRepository.findAllByActiveIsTrue();
    }


    @Override
    public Lot getLotById(Integer id) {
        return lotRepository.findByIdAndActiveTrue(id).
                orElseThrow(()-> new EntityNotFoundException(LOT, id));
    }


    @Override
    public void delete(Integer id) {
        Lot lot = this.getLotById(id);
        if (lot.getCurrentDocNumber()!= null) {
            throw new HasAsociatedEntityException(LOT, "Cargas");
        }
        lot.setActive(false);
        lotRepository.save(lot);
    }


    private Lot convertToEntity(LotDto dto) {
        return mapper.map(dto, Lot.class);
    }

    @Override
    public PagePojo<Lot> getByPageAndFilters(int page, int size, String sortBy,
                                                  String sortOrder, LotFilter filter) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Lot> spec = this.generateSpecification(filter);
        Page<Lot> filtered = lotRepository.findAll(spec, pageable);
        return this.fromPageToPagePojo(filtered);
    }

    public PagePojo<Lot> fromPageToPagePojo(Page<Lot> page){
        PagePojo<Lot> dto = new PagePojo<>();
        dto.setContent(page.getContent());
        dto.setLast(page.isLast());
        dto.setPageNumber(page.getNumber());
        dto.setPageSize(page.getSize());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        return dto;
    }

    @Override
    public List<Lot> getFiltered(LotFilter filter) {
        Specification<Lot> spec = this.generateSpecification(filter);
        return lotRepository.findAll(spec);
    }

    private Specification<Lot> generateSpecification(LotFilter filter) {
        FieldsFilterUtil fields = new FieldsFilterUtil();
        fields.addEqualsField("active", true);
        fields.addEqualsField("prefix",filter.getPrefix());
        fields.addLikeField("description", filter.getDescription());
        fields.addEqualsField("initialDocNumber", filter.getInitialDocNumber());
        fields.addEqualsField("currentDocNumber", filter.getCurrentDocNumber());
        fields.addLikeField("assignment", filter.getAssignment());
        fields.addEqualsField("state",filter.getState());
        return SpecificationUtils.createSpecification(fields.getFilterFields());
    }
}

