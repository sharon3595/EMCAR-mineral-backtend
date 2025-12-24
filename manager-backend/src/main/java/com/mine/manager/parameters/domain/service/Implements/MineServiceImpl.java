package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.exception.DuplicateException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.MineRepository;
import com.mine.manager.parameters.domain.entity.Mine;
import com.mine.manager.parameters.domain.mapper.MineMapper;
import com.mine.manager.parameters.domain.service.Interfaces.MineService;
import com.mine.manager.parameters.presentation.request.dto.MineDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MineServiceImpl extends CRUDServiceImpl<Mine, Integer> implements MineService {

    private final MineRepository mineRepository;
    private final MineMapper mineMapper;
    private static final String MINE_ENTITY = SpanishEntityNameProvider.getSpanishName("Mine");

    @Override
    protected GenericRepository<Mine, Integer> getRepository() {
        return mineRepository;
    }

    @Override
    public Mine create(MineDto dto) {
        if(mineRepository.existsByName(dto.getName())){
            throw new DuplicateException(MINE_ENTITY, "Nombre", dto.getName());
        }
        return super.create(mineMapper.fromDto(dto));
    }

    @Override
    public Mine update(Integer id, MineDto dto) {
        if(mineRepository.existsByNameAndIdNot(dto.getName(), id)){
            throw new DuplicateException(MINE_ENTITY, "Nombre", dto.getName());
        }
        return mineRepository.save(mineMapper.fromDto(dto, super.getById(id)));
    }
}