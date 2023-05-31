package org.painting.alutechorganizer.service;

import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.exc.WorkerNotFoundException;

import java.util.List;

public interface WorkerService {

    void saveWorker(WorkerDto workerDto, Integer masterId);

    List<WorkerDto> getAllWorkers();

    WorkerDto getWorkerBySurname(String surname) throws WorkerNotFoundException;

    void deleteWorkerById(Integer id);

    void updateWorker(WorkerDto newWorkerVersion, Integer id);

    List<WorkerDto> getWorkersByMasterId(Integer masterId);

    void setNewMasterToWorker(Integer workerId, Integer masterId);

    WorkerDto getWorkerById(Integer workerId);
}
