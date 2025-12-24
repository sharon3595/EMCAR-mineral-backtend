package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.exception.DuplicateException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.TypeMaterialRepository;
import com.mine.manager.parameters.domain.entity.TypeMaterial;
import com.mine.manager.parameters.domain.mapper.TypeMaterialMapper;
import com.mine.manager.parameters.domain.service.Interfaces.TypeMaterialService;
import com.mine.manager.parameters.presentation.request.dto.TypeMaterialDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class TypeMaterialServiceImpl extends CRUDServiceImpl<TypeMaterial, Integer> implements TypeMaterialService {


    private final TypeMaterialRepository typeTypeMaterialRepository;
    private final TypeMaterialMapper typeTypeMaterialMapper;
    private static final String MINE_ENTITY = SpanishEntityNameProvider.getSpanishName("TypeMaterial");


    @Override
    protected GenericRepository<TypeMaterial, Integer> getRepository() {
        return typeTypeMaterialRepository;
    }


    @Override
    public TypeMaterial create(TypeMaterialDto dto) {
        if(typeTypeMaterialRepository.existsByName(dto.getName())){
            throw new DuplicateException(MINE_ENTITY, "Nombre", dto.getName());
        }
        return super.create(typeTypeMaterialMapper.fromDto(dto));
    }


    @Override
    public TypeMaterial update(Integer id, TypeMaterialDto dto) {
        if(typeTypeMaterialRepository.existsByNameAndIdNot(dto.getName(), id)){
            throw new DuplicateException(MINE_ENTITY, "Nombre", dto.getName());
        }
        return typeTypeMaterialRepository.save(typeTypeMaterialMapper.fromDto(dto, super.getById(id)));
    }
}



