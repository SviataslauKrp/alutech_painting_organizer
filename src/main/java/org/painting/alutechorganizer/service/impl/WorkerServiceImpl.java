package org.painting.alutechorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkerNotFoundException;
import org.painting.alutechorganizer.mapper.WorkerMapper;
import org.painting.alutechorganizer.repository.MasterRepository;
import org.painting.alutechorganizer.repository.WorkerRepository;
import org.painting.alutechorganizer.service.WorkerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor

@Service
@Transactional
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final MasterRepository masterRepository;
    private final WorkerMapper mapper;

    @Override
    public void saveWorker(WorkerDto workerDto) {
        WorkerEntity workerEntity = mapper.toWorkerEntity(workerDto);
        workerRepository.save(workerEntity);
    }

    @Override
    public List<WorkerDto> getAllWorkers() {

        List<WorkerEntity> allWorkersEntities = workerRepository.findAll();
        return mapper.toListDtos(allWorkersEntities);

    }

    @Override
    public WorkerDto getWorkerById(Integer id) throws WorkerNotFoundException {

        WorkerEntity workerEntity = workerRepository.findById(id).orElseThrow(WorkerNotFoundException::new);
        return mapper.toWorkerDto(workerEntity);

    }

    @Override
    public void deleteWorkerById(Integer id) {
        workerRepository.deleteById(id);
    }

    @Override
    public void updateWorker(WorkerDto workerDto, Integer id) {

        WorkerEntity workerEntity = workerRepository.findById(id).orElseThrow(WorkerNotFoundException::new);
        mapper.updateWorkerFromDto(workerDto, workerEntity);

    }

    @Override
    public List<WorkerDto> getWorkersByMasterId(Integer masterId) {

        List<WorkerEntity> workersByMasterId = workerRepository.findByMasterId(masterId);
        return mapper.toListDtos(workersByMasterId);

    }

    @Override
    public List<WorkerDto> getWorkersByMasterIdAndWorkplaceId(Integer masterId, Integer workplaceId) {

        return mapper.toListDtos(workerRepository.findByMasterIdAndWorkplaceId(masterId, workplaceId));

    }

    @Override
    public void setNewMasterToWorker(Integer workerId, Integer masterId) {
        //или лучше сделать через линию мастеров?
        MasterEntity newMaster = masterRepository.findById(masterId).orElseThrow(MasterException::new);
        WorkerEntity worker = workerRepository.findById(workerId).orElseThrow(WorkerNotFoundException::new);
        newMaster.addWorker(worker);

    }

}
