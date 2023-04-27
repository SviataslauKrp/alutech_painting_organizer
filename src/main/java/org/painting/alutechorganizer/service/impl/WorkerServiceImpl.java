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
@Transactional
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository repository;
    private final WorkerMapper mapper;

    @Override
    public void saveWorker(WorkerDto workerDto) {
        WorkerEntity workerEntity = mapper.toWorkerEntity(workerDto);
        repository.save(workerEntity);
    }

    @Override
    public List<WorkerDto> getAllWorkers() throws EmptyBrigadeException {

        List<WorkerEntity> allWorkersEntities = repository.findAll();

        if (allWorkersEntities.size() < 1) {
            throw new EmptyBrigadeException();
        }
        return mapper.toListDtos(allWorkersEntities);

    }

    @Override
    public WorkerDto getWorkerById(Integer id) throws WorkerNotFoundException {

        Optional<WorkerEntity> optionalWorker = repository.findById(id);

        if (optionalWorker.isPresent()) {
            return mapper.toWorkerDto(optionalWorker.get());
        } else {
            throw new WorkerNotFoundException();
        }

    }

    @Override
    public void deleteWorkerById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void updateWorker(WorkerDto worker, Integer id) {

        Optional<WorkerEntity> optionalWorker = repository.findById(id);

        if (optionalWorker.isPresent()) {
            mapper.updateWorkerFromDto(worker, optionalWorker.get());
        } else {
            throw new WorkerNotFoundException();
        }

    }
}