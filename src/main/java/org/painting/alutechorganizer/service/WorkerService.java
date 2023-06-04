package org.painting.alutechorganizer.service;

import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.exc.WorkerNotFoundException;

import java.util.List;

public interface WorkerService {

    WorkerEntity setToMaster(WorkerDto workerDto, Integer masterId);

    List<WorkerDto> getAllWorkers();

    List<WorkerDto> getWorkerBySurnameAndMasterId(String surname, Integer masterId) throws WorkerNotFoundException;

    void deleteWorkerById(Integer id);

    void updateWorker(WorkerDto newWorkerVersion, Integer id);

    List<WorkerDto> getWorkersByMasterId(Integer masterId);

    void setNewMasterToWorker(Integer workerId, Integer masterId);

    WorkerDto getWorkerById(Integer workerId);
}
