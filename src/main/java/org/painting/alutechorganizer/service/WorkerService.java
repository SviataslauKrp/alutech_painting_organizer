package org.painting.alutechorganizer.service;

import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.WorkerDto;

import java.util.List;

public interface WorkerService {

    WorkerEntity setToMaster(WorkerDto worker, Integer masterId);

    List<WorkerDto> getAllWorkers();

    List<WorkerDto> getWorkerBySurnameAndMasterId(String surname, Integer masterId);

    void deleteWorkerById(Integer id);

    void updateWorker(WorkerDto newWorkerVersion, Integer id);

    List<WorkerDto> getWorkersByMasterId(Integer masterId);

    WorkerDto getWorkerById(Integer workerId);
}
