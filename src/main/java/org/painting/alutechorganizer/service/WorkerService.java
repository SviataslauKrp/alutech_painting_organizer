package org.painting.alutechorganizer.service;

import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.exc.EmptyBrigadeException;
import org.painting.alutechorganizer.exc.WorkerNotFoundException;

import java.util.List;
import java.util.Optional;

public interface WorkerService {

    void saveWorker(WorkerDto workerDto);

    List<WorkerDto> getAllWorkers() throws EmptyBrigadeException;
    WorkerDto getWorkerById(Integer id) throws WorkerNotFoundException;

    void deleteWorkerById(Integer id);

    void updateWorkerById(Integer id);
}
