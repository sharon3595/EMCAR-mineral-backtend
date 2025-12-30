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

import java.util.List;


@Service
@AllArgsConstructor
public class TypeMaterialServiceImpl extends CRUDServiceImpl<TypeMaterial, Integer> implements TypeMaterialService {


    private final TypeMaterialRepository typeMaterialRepository;
    private final TypeMaterialMapper typeMaterialMapper;
    private static final String TYPE_MATERIAL = SpanishEntityNameProvider.getSpanishName(TypeMaterial.class.getSimpleName());


    @Override
    protected GenericRepository<TypeMaterial, Integer> getRepository() {
        return typeMaterialRepository;
    }


    @Override
    public TypeMaterial create(TypeMaterialDto dto) {
        if(typeMaterialRepository.existsByName(dto.getName())){
            throw new DuplicateException(TYPE_MATERIAL, "Nombre", dto.getName());
        }
        return super.create(typeMaterialMapper.fromDto(dto));
    }


    @Override
    public TypeMaterial update(Integer id, TypeMaterialDto dto) {
        if(typeMaterialRepository.existsByNameAndIdNot(dto.getName(), id)){
            throw new DuplicateException(TYPE_MATERIAL, "Nombre", dto.getName());
        }
        return typeMaterialRepository.save(typeMaterialMapper.fromDto(dto, super.getById(id)));
    }

    @Override
    public List<TypeMaterial> getFilteredForSelect(String name, String description, String some) {
        String cleanName = (name != null && !name.isBlank()) ? name : null;
        String cleanDesc = (description != null && !description.isBlank()) ? description : null;
        String cleanSome = (some != null && !some.isBlank()) ? some : null;

        return typeMaterialRepository.searchByFilters(cleanName, cleanDesc, cleanSome);
    }
}



