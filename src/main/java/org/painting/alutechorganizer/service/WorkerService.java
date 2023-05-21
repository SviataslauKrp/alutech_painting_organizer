package org.painting.alutechorganizer.service;

import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.exc.WorkerNotFoundException;

import java.util.List;

public interface WorkerService {

    void saveWorker(WorkerDto workerDto);

    List<WorkerDto> getAllWorkers();

    WorkerDto getWorkerById(Integer id) throws WorkerNotFoundException;

    void deleteWorkerById(Integer id);

    void updateWorker(WorkerDto worker, Integer id);

    List<WorkerDto> getWorkersByMasterId(Integer masterId);
    List<WorkerDto> getWorkersByMasterIdAndWorkplaceId(Integer masterId, Integer workplaceId);

    void setNewMasterToWorker(Integer workerId, Integer masterId);
}
