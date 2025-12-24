package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.exception.DuplicateException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.MaterialRepository;
import com.mine.manager.parameters.domain.entity.Material;
import com.mine.manager.parameters.domain.mapper.MaterialMapper;
import com.mine.manager.parameters.domain.service.Interfaces.MaterialService;
import com.mine.manager.parameters.presentation.request.dto.MaterialDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MaterialServiceImpl extends CRUDServiceImpl<Material, Integer> implements MaterialService {


    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;
    private static final String MINE_ENTITY = SpanishEntityNameProvider.getSpanishName("Material");


    @Override
    protected GenericRepository<Material, Integer> getRepository() {
        return materialRepository;
    }


    @Override
    public Material create(MaterialDto dto) {
        if (materialRepository.existsByName(dto.getName())) {
            throw new DuplicateException(MINE_ENTITY, "Nombre", dto.getName());
        }
        return super.create(materialMapper.fromDto(dto));
    }


    @Override
    public Material update(Integer id, MaterialDto dto) {
        if (materialRepository.existsByNameAndIdNot(dto.getName(), id)) {
            throw new DuplicateException(MINE_ENTITY, "Nombre", dto.getName());
        }
        return materialRepository.save(materialMapper.fromDto(dto, super.getById(id)));
    }
}



