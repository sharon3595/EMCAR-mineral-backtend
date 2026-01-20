package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.exception.DuplicateException;
import com.mine.manager.exception.HasAsociatedEntityException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.LoadRepository;
import com.mine.manager.parameters.data.repository.MineralRepository;
import com.mine.manager.parameters.domain.entity.Mineral;
import com.mine.manager.parameters.domain.mapper.MineralMapper;
import com.mine.manager.parameters.domain.service.Interfaces.MineralService;
import com.mine.manager.parameters.presentation.request.dto.MineralDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class MineralServiceImpl extends CRUDServiceImpl<Mineral, Integer> implements MineralService {


    private final MineralRepository mineralRepository;
    private final LoadRepository loadRepository;
    private final MineralMapper mineralMapper;
    private static final String MINERAL = SpanishEntityNameProvider.getSpanishName(Mineral.class.getSimpleName());


    @Override
    protected GenericRepository<Mineral, Integer> getRepository() {
        return mineralRepository;
    }


    @Override
    public Mineral create(MineralDto dto) {
        if (mineralRepository.existsBySymbol(dto.getSymbol())) {
            throw new DuplicateException(MINERAL, "Símbolo", dto.getSymbol());
        }
        return super.create(mineralMapper.fromDto(dto));
    }


    @Override
    public Mineral update(Integer id, MineralDto dto) {
        if (mineralRepository.existsBySymbolAndIdNot(dto.getSymbol(), id)) {
            throw new DuplicateException(MINERAL, "Símbolo", dto.getSymbol());
        }
        return mineralRepository.save(mineralMapper.fromDto(dto, super.getById(id)));
    }


    @Override
    public List<Mineral> getFilteredForSelect(String name, String symbol, String some) {
        String cleanName = (name != null && !name.isBlank()) ? name : null;
        String cleanDesc = (symbol != null && !symbol.isBlank()) ? symbol : null;
        String cleanSome = (some != null && !some.isBlank()) ? some : null;


        return mineralRepository.searchByFilters(cleanName, cleanDesc, cleanSome);
    }

    @Override
    public void delete(Integer id) {
        Mineral toDelete = this.getById(id);
        if (loadRepository.existsByMineralId(id)) {
            throw new HasAsociatedEntityException(
                    MINERAL, "Cargas"
            );
        }
        mineralRepository.delete(toDelete);
    }
}