package org.painting.alutechorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.mapper.WorkerMapper;
import org.painting.alutechorganizer.repository.WorkerRepository;
import org.painting.alutechorganizer.service.WorkerService;
import org.springframework.stereotype.Service;

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
}
