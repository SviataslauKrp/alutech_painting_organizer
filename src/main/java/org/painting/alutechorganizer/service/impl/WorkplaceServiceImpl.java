package org.painting.alutechorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.painting.alutechorganizer.dto.WorkplaceDto;
import org.painting.alutechorganizer.exc.EmptyWorkplacesListException;
import org.painting.alutechorganizer.exc.WorkerIsNotAvailableException;
import org.painting.alutechorganizer.exc.WorkerNotFoundException;
import org.painting.alutechorganizer.exc.WorkplaceException;
import org.painting.alutechorganizer.mapper.WorkplaceMapper;
import org.painting.alutechorganizer.repository.WorkerRepository;
import org.painting.alutechorganizer.repository.WorkplaceRepository;
import org.painting.alutechorganizer.service.WorkplaceService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor

@Service
@Transactional
public class WorkplaceServiceImpl implements WorkplaceService {

    private final WorkplaceMapper workplaceMapper;
    private final WorkplaceRepository workplaceRepository;
    private final WorkerRepository workerRepository;

    @Override
    public void saveWorkplace(WorkplaceDto workplace) {

        WorkplaceEntity workplaceEntity = workplaceMapper.toWorkplaceEntity(workplace);
        workplaceRepository.save(workplaceEntity);

    }

    @Override
    public List<WorkplaceDto> getAllWorkplaces() {

        List<WorkplaceEntity> allWorkplaceEntities = workplaceRepository.findAll();
        if (allWorkplaceEntities.isEmpty()) {
            throw new EmptyWorkplacesListException();
        }
        return workplaceMapper.toListDtos(allWorkplaceEntities);

    }

    @Override
    public WorkplaceDto getWorkplaceById(Integer id) {

        WorkplaceEntity workplaceEntity = workplaceRepository.findById(id).orElseThrow(WorkplaceException::new);
        return workplaceMapper.toWorkplaceDto(workplaceEntity);

    }

    @Override
    public void deleteWorkplaceById(Integer id) {

        WorkplaceEntity workplaceEntity = workplaceRepository.findById(id).orElseThrow(WorkplaceException::new);
        workplaceEntity.getWorkers().forEach(WorkerEntity::leaveWorkplace);
        workplaceRepository.deleteById(id);

    }

    @Override
    public void updateWorkplaceById(WorkplaceDto workplaceDto, Integer id) {

        WorkplaceEntity workplaceEntity = workplaceRepository.findById(id).orElseThrow(WorkplaceException::new);
        workplaceMapper.updateWorkplaceFromDto(workplaceDto, workplaceEntity);

    }

    @Override
    public void addWorkerToWorkplace(Integer workerId, Integer workplaceId) {

        WorkplaceEntity workplaceEntity = workplaceRepository.findById(workplaceId).orElseThrow(WorkplaceException::new);
        WorkerEntity workerEntity = workerRepository.findById(workerId).orElseThrow(WorkerNotFoundException::new);

        if (workerEntity.isAvailable()) {
            workplaceEntity.addWorker(workerEntity);
        } else {
            throw new WorkerIsNotAvailableException();
        }

    }

    @Override
    public void removeWorkerFromWorkplace(Integer workerId, Integer workplaceId) {

        WorkplaceEntity workplaceEntity = workplaceRepository.findById(workplaceId).orElseThrow(WorkplaceException::new);
        WorkerEntity workerEntity = workerRepository.findById(workerId).orElseThrow(WorkerNotFoundException::new);

        if (workplaceEntity.getWorkers().contains(workerEntity)) {
            workplaceEntity.removeWorker(workerEntity);
        } else {
            throw new WorkerNotFoundException();
        }

    }

}
