package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.common.SpecificationUtils;
import com.mine.manager.exception.DuplicateException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.MineRepository;
import com.mine.manager.parameters.domain.entity.Mine;
import com.mine.manager.parameters.domain.mapper.MineMapper;
import com.mine.manager.parameters.domain.service.Interfaces.MineService;
import com.mine.manager.parameters.presentation.request.dto.MineDto;
import com.mine.manager.util.FieldsFilterUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MineServiceImpl extends CRUDServiceImpl<Mine, Integer> implements MineService {

    private final MineRepository mineRepository;
    private final MineMapper mineMapper;
    private static final String MINE_ENTITY = SpanishEntityNameProvider.getSpanishName(Mine.class.getSimpleName());

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

    @Override
    public List<Mine> getFilteredForSelect(String name, String description, String some) {
        String cleanName = (name != null && !name.isBlank()) ? name : null;
        String cleanDesc = (description != null && !description.isBlank()) ? description : null;
        String cleanSome = (some != null && !some.isBlank()) ? some : null;

        return mineRepository.searchByFilters(cleanName, cleanDesc, cleanSome);
    }
}