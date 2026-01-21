package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.common.enums.LotTypeEnum;
import com.mine.manager.exception.EntityNotFoundException;
import com.mine.manager.exception.InvalidValueException;
import com.mine.manager.parameters.data.repository.AdvanceRepository;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.domain.entity.Advance;
import com.mine.manager.parameters.domain.entity.Lot;
import com.mine.manager.parameters.domain.service.Interfaces.AdvanceService;
import com.mine.manager.parameters.domain.service.Interfaces.LoadService;
import com.mine.manager.parameters.domain.service.Interfaces.LotService;
import com.mine.manager.parameters.presentation.request.dto.AdvanceDto;
import com.mine.manager.parameters.presentation.response.pojo.AdvancePojo;
import com.mine.manager.parameters.presentation.response.pojo.CorrelativePojo;
import com.mine.manager.parameters.presentation.response.pojo.TotalAdvancesByLoadPojo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class AdvanceServiceImpl extends CRUDServiceImpl<Advance, Integer> implements
        AdvanceService {

    private final AdvanceRepository advanceRepository;
    private final LoadService loadService;
    private final LotService lotService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;
    private static final String ADVANCE = SpanishEntityNameProvider.getSpanishName(Advance.class.getSimpleName());

    @Override
    protected GenericRepository<Advance, Integer> getRepository() {
        return advanceRepository;
    }

    @Override
    public AdvancePojo create(AdvanceDto dto) {
        Advance advance = convertToEntity(dto);
        advance.setId(null);
        advance.setLoad(loadService.getById(dto.getLoadId()));
        Lot lot = lotService.getLotById(dto.getLotId());
        if (!lot.getAssignment().equals(LotTypeEnum.RECEIPT)) {
            throw new InvalidValueException("No se puede generar el correlativo: el lote indicado no es de tipo Recibo.");
        }
        advance.setLot(lot);
        CorrelativePojo correlative = loadService.processCorrelative(dto.getLotId(), true);
        advance.setReceiptCode(correlative.getCorrelative());
        advanceRepository.save(advance);
        return new AdvancePojo(advance);
    }

    @Override
    public AdvancePojo update(Integer id, AdvanceDto dto) {
        Advance advanceFound = this.getById(id);
        Advance updatedAdvance = updateAdvanceEntity(advanceFound, dto);
        advanceRepository.save(updatedAdvance);
        return new AdvancePojo(updatedAdvance);
    }


    private Advance updateAdvanceEntity(Advance advanceFound, AdvanceDto dto) {
        Advance advance = convertToEntity(dto);
        advance.setId(advanceFound.getId());
        advance.setLoad(advanceFound.getLoad());
        advance.setLot(advanceFound.getLot());
        advance.setReceiptCode(advanceFound.getReceiptCode());
        return advance;
    }


    @Override
    public List<AdvancePojo> getAdvances() {
        return advanceRepository.findAllByActiveIsTrue().stream()
                .map(AdvancePojo::new)
                .toList();
    }

    @Override
    public AdvancePojo getAdvanceById(Integer id) {
        return new AdvancePojo(this.getActiveAdvanceById(id));
    }

    private Advance getActiveAdvanceById(Integer id) {
        return advanceRepository.findByIdAndActiveTrue(id).
                orElseThrow(() -> new EntityNotFoundException(ADVANCE, id));
    }

    @Override
    public void delete(Integer id) {
        Advance advance = this.getActiveAdvanceById(id);
        advance.setActive(false);
        advanceRepository.save(advance);
    }

    @Override
    public List<AdvancePojo> getAdvancesByLoadId(Integer id) {
        return advanceRepository.findAllByLoadIdAndActiveIsTrue(id).stream().map(AdvancePojo::new).toList();
    }

    @Override
    public TotalAdvancesByLoadPojo getTotalAdvancesByLoad(Integer loadId) {
        BigDecimal total = advanceRepository.sumAmountByLoadId(loadId);
        return new TotalAdvancesByLoadPojo(loadId, total);
    }

    private Advance convertToEntity(AdvanceDto dto) {
        return mapper.map(dto, Advance.class);
    }
}
