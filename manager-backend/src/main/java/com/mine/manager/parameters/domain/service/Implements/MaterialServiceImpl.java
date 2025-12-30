package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.exception.DuplicateException;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.MaterialRepository;
import com.mine.manager.parameters.domain.entity.Material;
import com.mine.manager.parameters.domain.entity.Mine;
import com.mine.manager.parameters.domain.mapper.MaterialMapper;
import com.mine.manager.parameters.domain.service.Interfaces.MaterialService;
import com.mine.manager.parameters.presentation.request.dto.MaterialDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class MaterialServiceImpl extends CRUDServiceImpl<Material, Integer> implements MaterialService {


    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;
    private static final String MATERIAL = SpanishEntityNameProvider.getSpanishName(Material.class.getSimpleName());


    @Override
    protected GenericRepository<Material, Integer> getRepository() {
        return materialRepository;
    }


    @Override
    public Material create(MaterialDto dto) {
        if (materialRepository.existsByName(dto.getName())) {
            throw new DuplicateException(MATERIAL, "Nombre", dto.getName());
        }
        return super.create(materialMapper.fromDto(dto));
    }


    @Override
    public Material update(Integer id, MaterialDto dto) {
        if (materialRepository.existsByNameAndIdNot(dto.getName(), id)) {
            throw new DuplicateException(MATERIAL, "Nombre", dto.getName());
        }
        return materialRepository.save(materialMapper.fromDto(dto, super.getById(id)));
    }

    @Override
    public List<Material> getFilteredForSelect(String name, String description, String some) {
        String cleanName = (name != null && !name.isBlank()) ? name : null;
        String cleanDesc = (description != null && !description.isBlank()) ? description : null;
        String cleanSome = (some != null && !some.isBlank()) ? some : null;

        return materialRepository.searchByFilters(cleanName, cleanDesc, cleanSome);
    }
}



