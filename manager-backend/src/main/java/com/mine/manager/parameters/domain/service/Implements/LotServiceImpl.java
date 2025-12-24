package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.exception.DuplicateException;
import com.mine.manager.exception.EntityNotFoundException;
import com.mine.manager.exception.HasAsociatedEntityException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.LotRepository;
import com.mine.manager.parameters.domain.entity.Lot;
import com.mine.manager.parameters.domain.service.Interfaces.LotService;
import com.mine.manager.parameters.presentation.request.dto.LotDto;
import com.mine.manager.util.StringUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LotServiceImpl extends CRUDServiceImpl<Lot, Integer> implements
        LotService {


    private final LotRepository lotRepository;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;
    private static final String LOT = "Lote";

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
}

