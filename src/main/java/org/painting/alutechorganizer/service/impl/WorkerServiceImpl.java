package org.painting.alutechorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.exc.EmptyBrigadeException;
import org.painting.alutechorganizer.exc.WorkerNotFoundException;
import org.painting.alutechorganizer.mapper.WorkerMapper;
import org.painting.alutechorganizer.repository.WorkerRepository;
import org.painting.alutechorganizer.service.WorkerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository repository;
    private final WorkerMapper mapper;

    @Override
    public void saveWorker(WorkerDto workerDto) {
        WorkerEntity workerEntity = mapper.toEntity(workerDto);
        repository.save(workerEntity);
    }

    @Override
    public List<WorkerDto> getAllWorkers() {
        List<WorkerEntity> entities = repository.findAll();
        if (entities.size() < 1) {
            throw new EmptyBrigadeException();
        }
        return mapper.toListDto(entities);
    }

    @Override
    public WorkerDto getWorkerById(Integer id) {
        Optional<WorkerEntity> byId = repository.findById(id);
        if (byId.isPresent()) {
            return mapper.toDto(byId.get());
        } else {
            throw new WorkerNotFoundException();
        }
    }

    @Override
    @Transactional
    public void deleteWorkerById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateWorkerById(Integer id) {

        Optional<WorkerEntity> byId = repository.findById(id);
        if (byId.isPresent()) {



        }
    }
}
