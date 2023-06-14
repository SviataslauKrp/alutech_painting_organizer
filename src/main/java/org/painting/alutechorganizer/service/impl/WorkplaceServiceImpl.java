package org.painting.alutechorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.painting.alutechorganizer.dto.WorkplaceDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkerException;
import org.painting.alutechorganizer.exc.WorkplaceException;
import org.painting.alutechorganizer.mapper.WorkplaceMapper;
import org.painting.alutechorganizer.repository.MasterRepository;
import org.painting.alutechorganizer.repository.WorkerRepository;
import org.painting.alutechorganizer.repository.WorkplaceRepository;
import org.painting.alutechorganizer.service.WorkplaceService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor

@Service
public class WorkplaceServiceImpl implements WorkplaceService {

    private final WorkplaceMapper workplaceMapper;
    private final WorkplaceRepository workplaceRepository;
    private final WorkerRepository workerRepository;
    private final MasterRepository masterRepository;

    @Override
    public void saveWorkplace(WorkplaceDto workplace, Integer masterId) {
        MasterEntity master = masterRepository.findById(masterId).orElseThrow(() -> new MasterException("The master isn't found"));
        WorkplaceEntity workplaceEntity = workplaceMapper.toWorkplaceEntity(workplace);
        master.addWorkplace(workplaceEntity);
        workplaceRepository.save(workplaceEntity);
    }

    @Override
    public List<WorkplaceDto> getAllWorkplaces() {

        List<WorkplaceEntity> allWorkplaceEntities = workplaceRepository.findAll();
        return workplaceMapper.toListDtos(allWorkplaceEntities);
    }

    @Override
    public WorkplaceDto getWorkplaceById(Integer id) {

        WorkplaceEntity workplaceEntity = workplaceRepository.findById(id).orElseThrow(WorkplaceException::new);
        return workplaceMapper.toWorkplaceDto(workplaceEntity);
    }

    @Transactional
    @Override
    public void deleteWorkplaceById(Integer id) {
        WorkplaceEntity workplaceEntity = workplaceRepository.findById(id).orElseThrow(WorkplaceException::new);
        workplaceEntity.getWorkers().forEach(WorkerEntity::leaveWorkplace);
        workplaceRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateWorkplaceById(WorkplaceDto workplaceDto, Integer id) {
        WorkplaceEntity workplaceEntity = workplaceRepository.findById(id).orElseThrow(WorkplaceException::new);
        workplaceMapper.updateWorkplaceFromDto(workplaceDto, workplaceEntity);
    }

    @Transactional
    @Override
    public void addWorkerToWorkplace(Integer workerId, Integer workplaceId) {
        WorkplaceEntity workplaceEntity = workplaceRepository.findById(workplaceId).orElseThrow(WorkplaceException::new);
        WorkerEntity workerEntity = workerRepository.findById(workerId).orElseThrow(WorkerException::new);

        if (workerEntity.getWorkplace() == null) {
            workplaceEntity.addWorker(workerEntity);
        } else {
            throw new WorkerException();
        }
    }

    @Transactional
    @Override
    public void removeWorkerFromWorkplace(Integer workerId, Integer workplaceId) {
        WorkplaceEntity workplaceEntity = workplaceRepository.findById(workplaceId).orElseThrow(WorkplaceException::new);
        WorkerEntity workerEntity = workerRepository.findById(workerId).orElseThrow(WorkerException::new);

        if (workplaceEntity.getWorkers().contains(workerEntity)) {
            workplaceEntity.removeWorker(workerEntity);
        } else {
            throw new WorkerException();
        }
    }

    @Override
    public List<WorkplaceDto> getWorkplacesByMasterId(Integer masterId) {
        return workplaceMapper.toListDtos(workplaceRepository.findByMasterId(masterId));
    }

}
