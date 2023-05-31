package org.painting.alutechorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkerException;
import org.painting.alutechorganizer.mapper.WorkerMapper;
import org.painting.alutechorganizer.repository.MasterRepository;
import org.painting.alutechorganizer.repository.WorkerRepository;
import org.painting.alutechorganizer.service.WorkerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor

@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final MasterRepository masterRepository;
    private final WorkerMapper mapper;

    @Override
    public void saveWorker(WorkerDto workerDto, Integer masterId) {
        MasterEntity masterEntity = masterRepository.findById(masterId).orElseThrow(() -> new MasterException("The master isn't found"));
        WorkerEntity workerEntity = mapper.toWorkerEntity(workerDto);
        workerEntity.setMaster(masterEntity);
        workerRepository.save(workerEntity);
    }

    @Override
    public List<WorkerDto> getAllWorkers() {

        List<WorkerEntity> allWorkersEntities = workerRepository.findAll();
        return mapper.toListDtos(allWorkersEntities);

    }

    @Override
    public WorkerDto getWorkerBySurname(String surname) {

        WorkerEntity workerEntity = workerRepository.findBySurname(surname).orElseThrow(() -> new WorkerException("The worker isn't found"));
        return mapper.toWorkerDto(workerEntity);

    }

    @Transactional
    @Override
    public void deleteWorkerById(Integer id) {
        workerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateWorker(WorkerDto newWorkerVersion,
                             Integer id) {

        WorkerEntity oldWorkerVersion = workerRepository.findById(id).orElseThrow(() -> new WorkerException("The worker isn't found"));

        if (oldWorkerVersion.getIsAvailable() && !newWorkerVersion.getIsAvailable()) {
            oldWorkerVersion.setWorkplace(null);
        }

        mapper.updateWorkerFromDto(newWorkerVersion, oldWorkerVersion);

    }

    @Override
    public List<WorkerDto> getWorkersByMasterId(Integer masterId) {

        List<WorkerEntity> workersByMasterId = workerRepository.findByMasterId(masterId);
        return mapper.toListDtos(workersByMasterId);

    }

    @Transactional
    @Override
    public void setNewMasterToWorker(Integer workerId, Integer masterId) {
        MasterEntity newMaster = masterRepository.findById(masterId).orElseThrow(() -> new MasterException("The master isn't found"));
        WorkerEntity worker = workerRepository.findById(workerId).orElseThrow(() -> new WorkerException("The worker isn't found"));
        newMaster.addWorker(worker);

    }

    @Override
    public WorkerDto getWorkerById(Integer workerId) {
        WorkerEntity workerEntity = workerRepository.findById(workerId).orElseThrow(() -> new WorkerException("The worker isn't found"));
        return mapper.toWorkerDto(workerEntity);
    }

}
