package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.common.SpecificationUtils;
import com.mine.manager.common.enums.LotTypeEnum;
import com.mine.manager.common.enums.StateLoadEnum;
import com.mine.manager.exception.EntityNotFoundException;
import com.mine.manager.exception.HasAsociatedEntityException;
import com.mine.manager.exception.InvalidValueException;
import com.mine.manager.parameters.data.repository.*;
import com.mine.manager.parameters.domain.entity.Load;
import com.mine.manager.parameters.domain.entity.Lot;
import com.mine.manager.parameters.domain.mapper.LoadMapper;
import com.mine.manager.parameters.domain.service.Interfaces.*;
import com.mine.manager.parameters.presentation.request.dto.LoadDto;
import com.mine.manager.parameters.presentation.request.filter.LoadFilter;
import com.mine.manager.parameters.presentation.response.pojo.CorrelativePojo;
import com.mine.manager.parameters.presentation.response.pojo.LoadPojo;
import com.mine.manager.parameters.presentation.response.pojo.LotPojo;
import com.mine.manager.parameters.presentation.response.pojo.PagePojo;
import com.mine.manager.util.CodeGeneratorUtil;
import com.mine.manager.util.FieldsFilterUtil;
import com.mine.manager.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class LoadServiceImpl extends CRUDServiceImpl<Load, Integer> implements
        LoadService {

    private final LoadRepository loadRepository;
    private final LoadMapper loadMapper;
    private final SupplierService supplierService;
    private final LotService lotService;
    private final MineralService mineralService;
    private final TypeMineralService typeMineralService;
    private final MineService mineService;
    private final CooperativeService cooperativeService;
    private final AdvanceRepository advanceRepository;
    private final LiquidationRepository liquidationRepository;
    private final LotRepository lotRepository;

    private static final String LOAD = SpanishEntityNameProvider.getSpanishName(Load.class.getSimpleName());

    @Override
    public List<LoadPojo> getLoads() {
        List<Load> list = loadRepository.findAll();
        return loadMapper.toPojoList(list);
    }

    @Override
    public LoadPojo getLoadById(Integer id) {
        Load load = loadRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException(LOAD, id.toString()));
        return loadMapper.toPojo(load);
    }

    @Override
    @Transactional
    public LoadPojo create(LoadDto dto) {
        Load load = new Load();
        loadMapper.fromDto(dto, load);
        load.setExternalLot(CodeGeneratorUtil.generate(6));
        this.updateEntities(dto, load, false);
        CorrelativePojo correlative = this.processCorrelative(load.getLot().getId(), true);
        load.setCorrelativeLotCode(correlative.getCorrelative());
        load.setCurrentDocNumber(correlative.getCurrentDocNumber());
        load.setState(StateLoadEnum.PENDING);
        Load savedLoad = loadRepository.save(load);
        return loadMapper.toPojo(savedLoad);
    }

    private void updateEntities(LoadDto dto, Load load, boolean isUpdate) {
        load.setSupplier(supplierService.getById(dto.getSupplierId()));
        if (!isUpdate) {
            Lot lot = lotService.getLotById(dto.getLotId());
            if (!lot.getAssignment().equals(LotTypeEnum.RECEPTION)) {
                throw new InvalidValueException("No se puede generar el correlativo: el lote indicado no es de tipo Recepción.");
            }
            load.setLot(lotService.getLotById(dto.getLotId()));
        }
        load.setMineral(mineralService.getById(dto.getMineralId()));
        load.setTypeMineral(typeMineralService.getById(dto.getTypeMineralId()));

        if (dto.getMineId() != null) {
            load.setMine(mineService.getById(dto.getMineId()));
        } else {
            load.setMine(null);
        }

        if (dto.getCooperativeId() != null) {
            load.setCooperative(cooperativeService.getById(dto.getCooperativeId()));
        } else {
            load.setCooperative(null);
        }
    }

    @Override
    @Transactional
    public LoadPojo update(Integer id, LoadDto dto) {
        Load load = this.getById(id);
        load.setDate(dto.getDate());
        load.setNumberSacks(dto.getNumberSacks());
        load.setWeight(dto.getWeight());
        load.setObservation(dto.getObservation());
        updateEntities(dto, load, true);
        Load savedLoad = loadRepository.save(load);
        return loadMapper.toPojo(savedLoad);
    }

    @Override
    protected GenericRepository<Load, Integer> getRepository() {
        return loadRepository;
    }

    @Override
    public void delete(Integer id) {
        Load load = this.getById(id);
        if (advanceRepository.existsByLoadIdAndActiveTrue(id)) {
            throw new HasAsociatedEntityException(
                    "No se puede eliminar la carga porque tiene anticipos asociados."
            );
        }
        if (liquidationRepository.existsByLoadIdAndActiveTrue(id)) {
            throw new HasAsociatedEntityException(
                    "No se puede eliminar la carga porque tiene liquidaciones asociadas."
            );
        }
        /*Lot lot = load.getLot();
        if (lot.getCurrentDocNumber() != null && lot.getCurrentDocNumber().equals(load.getCurrentDocNumber())) {

            int nextDocNumber = lot.getCurrentDocNumber() - 1;
            if (nextDocNumber < lot.getInitialDocNumber()) {
                lot.setCurrentDocNumber(null);
            } else {
                lot.setCurrentDocNumber(nextDocNumber);
            }

            lotRepository.save(lot);
        }*/
        load.setActive(false);
        load.setState(StateLoadEnum.DELETE);
        loadRepository.save(load);
    }

    @Override
    public CorrelativePojo processCorrelative(Integer lotId, boolean applyChange) {
        Lot lot = lotService.getLotById(lotId);

        Integer nextNumber;
        if (lot.getCurrentDocNumber() == null) {
            nextNumber = lot.getInitialDocNumber();
        } else {
            nextNumber = lot.getCurrentDocNumber() + 1;
        }

        String code = StringUtil.concatenate(lot.getPrefix(), nextNumber.toString(), "").trim();

        if (applyChange) {
            lot.setCurrentDocNumber(nextNumber);
        }

        return new CorrelativePojo(code, nextNumber);
    }

    @Override
    public PagePojo<LoadPojo> getByPageAndFilters(int page, int size, String sortBy,
                                                  String sortOrder, LoadFilter filter) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Load> spec = this.generateSpecification(filter);
        Page<Load> filtered = loadRepository.findAll(spec, pageable);
        return loadMapper.fromPageToPagePojo(filtered);
    }

    @Override
    public List<LoadPojo> getFiltered(LoadFilter filter) {
        Specification<Load> spec = this.generateSpecification(filter);
        List<Load> filtered = loadRepository.findAll(spec);
        return loadMapper.toPojoList(filtered);
    }

    private Specification<Load> generateSpecification(LoadFilter filter) {
        FieldsFilterUtil fields = new FieldsFilterUtil();
        fields.addEqualsField("active", filter.getActive());
        fields.addLikeField("correlativeLotCode", filter.getCorrelativeLotCode());
        fields.addLikeField("supplier.name", filter.getSupplierName());
        fields.addLikeField("state", filter.getState());
        fields.addLikeField("lot.description", filter.getLotDescription());
        fields.addDateField("date", filter.getStartDate()
                , filter.getEndDate() != null ? filter.getEndDate() : LocalDate.now());
        if (filter.getSome() != null && !filter.getSome().isBlank()) {
            List<Integer> someIds = loadRepository.findIdsBySome(filter.getSome());
            fields.addInSomeField("id", someIds);
        }
        return SpecificationUtils.createSpecification(fields.getFilterFields());
    }
}
