package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.exception.DuplicateException;
import com.mine.manager.exception.HasAsociatedEntityException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.LoadRepository;
import com.mine.manager.parameters.data.repository.TypeMineralRepository;
import com.mine.manager.parameters.domain.entity.TypeMineral;
import com.mine.manager.parameters.domain.mapper.TypeMineralMapper;
import com.mine.manager.parameters.domain.service.Interfaces.TypeMineralService;
import com.mine.manager.parameters.presentation.request.dto.TypeMineralDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class TypeMineralServiceImpl extends CRUDServiceImpl<TypeMineral, Integer> implements TypeMineralService {


    private final TypeMineralRepository typeMineralRepository;
    private final TypeMineralMapper typeMineralMapper;
    private final LoadRepository loadRepository;
    private static final String TYPE_MINERAL = SpanishEntityNameProvider.getSpanishName(TypeMineral.class.getSimpleName());


    @Override
    protected GenericRepository<TypeMineral, Integer> getRepository() {
        return typeMineralRepository;
    }


    @Override
    public TypeMineral create(TypeMineralDto dto) {
        if (typeMineralRepository.existsByName(dto.getName())) {
            throw new DuplicateException(TYPE_MINERAL, "Nombre", dto.getName());
        }
        return super.create(typeMineralMapper.fromDto(dto));
    }


    @Override
    public TypeMineral update(Integer id, TypeMineralDto dto) {
        if (typeMineralRepository.existsByNameAndIdNot(dto.getName(), id)) {
            throw new DuplicateException(TYPE_MINERAL, "Nombre", dto.getName());
        }
        return typeMineralRepository.save(typeMineralMapper.fromDto(dto, super.getById(id)));
    }


    @Override
    public List<TypeMineral> getFilteredForSelect(String name, String symbol, String some) {
        String namePattern = (name != null && !name.isBlank()) ? "%" + name + "%" : null;
        String symbolPattern = (symbol != null && !symbol.isBlank()) ? "%" + symbol + "%" : null;
        String somePattern = (some != null && !some.isBlank()) ? "%" + some + "%" : null;

        return typeMineralRepository.searchByFilters(namePattern, symbolPattern, somePattern);
    }

    @Override
    public void delete(Integer id) {
        TypeMineral toDelete = this.getById(id);
        if (loadRepository.existsByTypeMineralId(id)) {
            throw new HasAsociatedEntityException(
                    TYPE_MINERAL, "Cargas"
            );
        }
        typeMineralRepository.delete(toDelete);
    }
}




