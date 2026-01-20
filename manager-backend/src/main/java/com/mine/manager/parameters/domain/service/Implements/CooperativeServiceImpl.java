package com.mine.manager.parameters.domain.service.Implements;

import com.mine.manager.common.SpanishEntityNameProvider;
import com.mine.manager.exception.DuplicateException;
import com.mine.manager.exception.HasAsociatedEntityException;
import com.mine.manager.parameters.data.repository.CooperativeRepository;
import com.mine.manager.parameters.data.repository.GenericRepository;
import com.mine.manager.parameters.data.repository.LoadRepository;
import com.mine.manager.parameters.domain.entity.Cooperative;
import com.mine.manager.parameters.domain.service.Interfaces.CooperativeService;
import com.mine.manager.parameters.presentation.request.dto.CooperativeDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CooperativeServiceImpl extends CRUDServiceImpl<Cooperative, Integer> implements CooperativeService {


    private final CooperativeRepository cooperativeRepository;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;
    private final LoadRepository loadRepository;
    private static final String COOPERATIVE = SpanishEntityNameProvider.getSpanishName(Cooperative.class.getSimpleName());


    @Override
    protected GenericRepository<Cooperative, Integer> getRepository() {
        return cooperativeRepository;
    }


    @Override
    public Cooperative create(CooperativeDto dto) {
        if (cooperativeRepository.existsByName(dto.getName())) {
            throw new DuplicateException(COOPERATIVE, "Nombre", dto.getName());
        }
        return super.create(this.convertToEntity(dto));
    }


    @Override
    public Cooperative update(Integer id, CooperativeDto dto) {
        if (cooperativeRepository.existsByNameAndIdNot(dto.getName(), id)) {
            throw new DuplicateException(COOPERATIVE, "Nombre", dto.getName());
        }
        Cooperative updatedCooperative = updateCooperativeEntity(super.getById(id), dto);
        return cooperativeRepository.save(updatedCooperative);
    }


    private Cooperative updateCooperativeEntity(Cooperative cooperativeFound, CooperativeDto dto) {
        Cooperative cooperative = convertToEntity(dto);
        cooperative.setId(cooperativeFound.getId());
        return cooperative;
    }

    private Cooperative convertToEntity(CooperativeDto dto) {
        return mapper.map(dto, Cooperative.class);
    }

    @Override
    public void delete(Integer id) {
        Cooperative toDelete = this.getById(id);
        if (loadRepository.existsByCooperativeId(id)) {
            throw new HasAsociatedEntityException(
                    COOPERATIVE, "Cargas"
            );
        }
        cooperativeRepository.delete(toDelete);
    }
}







