package org.painting.alutechorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.dto.WorkplaceDto;
import org.painting.alutechorganizer.exc.EmptyWorkplacesListException;
import org.painting.alutechorganizer.exc.WorkplaceException;
import org.painting.alutechorganizer.mapper.WorkplaceMapper;
import org.painting.alutechorganizer.repository.WorkplaceRepository;
import org.painting.alutechorganizer.service.WorkplaceService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional
public class WorkplaceServiceImpl implements WorkplaceService {

    private final WorkplaceMapper mapper;
    private final WorkplaceRepository repository;

    @Override
    public void saveWorkplace(WorkplaceDto workplace) {

        WorkplaceEntity workplaceEntity = mapper.toWorkplaceEntity(workplace);
        repository.save(workplaceEntity);

    }

    @Override
    public List<WorkplaceDto> getAllWorkplaces() {

        List<WorkplaceEntity> allWorkplaceEntities = repository.findAll();
        if (allWorkplaceEntities.size() < 1) {
            throw new EmptyWorkplacesListException();
        }
        return mapper.toListDtos(allWorkplaceEntities);

    }

    @Override
    public WorkplaceDto getWorkplaceById(Integer id) {

        Optional<WorkplaceEntity> optionalWorkplaceEntity = repository.findById(id);

        if (optionalWorkplaceEntity.isPresent()) {
            return mapper.toWorkplaceDto(optionalWorkplaceEntity.get());
        } else {
            throw new WorkplaceException();
        }

    }

    @Override
    public void deleteWorkplaceById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void updateWorkplaceById(WorkplaceDto workplaceDto, Integer id) {

        Optional<WorkplaceEntity> optionalWorkplaceEntity = repository.findById(id);

        if (optionalWorkplaceEntity.isPresent()) {
            mapper.updateWorkplaceFromDto(workplaceDto, optionalWorkplaceEntity.get());
        } else {
            throw new WorkplaceException();
        }

    }
}
