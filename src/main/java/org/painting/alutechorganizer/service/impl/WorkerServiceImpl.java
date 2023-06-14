package org.painting.alutechorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkerException;
import org.painting.alutechorganizer.mapper.WorkerMapper;
import org.painting.alutechorganizer.repository.MasterRepository;
import org.painting.alutechorganizer.repository.UserEmployeeRepository;
import org.painting.alutechorganizer.repository.WorkerRepository;
import org.painting.alutechorganizer.service.WorkerService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor

@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final MasterRepository masterRepository;
    private final WorkerMapper mapper;
    private final UserEmployeeRepository userRepository;

    @Override
    public List<WorkerDto> getAllWorkers() {

        List<WorkerEntity> allWorkersEntities = workerRepository.findAll();
        return mapper.toListDtos(allWorkersEntities);
    }

    @Override
    public List<WorkerDto> getWorkerBySurnameAndMasterId(String surname, Integer masterId) {

        List<WorkerEntity> workerEntity = workerRepository.findBySurnameAndMasterId(surname, masterId).orElseThrow(() -> new WorkerException("The worker isn't found"));
        return mapper.toListDtos(workerEntity);
    }

    @Transactional
    @Override
    public void deleteWorkerById(Integer id) {
        UserEmployee user = userRepository.findByWorkerId(id).orElseThrow(() -> new UsernameNotFoundException("There is no such user"));
        userRepository.delete(user);
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
    public WorkerEntity setToMaster(WorkerDto workerDto, Integer masterId) {
        MasterEntity newMaster = masterRepository.findById(masterId).orElseThrow(() -> new MasterException("The master isn't found"));
        WorkerEntity workerEntity;
        if (workerDto.getId() != null) {
            workerEntity = workerRepository.findById(workerDto.getId()).orElseThrow(() -> new WorkerException("The worker isn't found"));
        } else {
            workerEntity = mapper.toWorkerEntity(workerDto);
        }
        newMaster.addWorker(workerEntity);
        workerRepository.save(workerEntity);
        return workerEntity;
    }

    @Override
    public WorkerDto getWorkerById(Integer workerId) {
        WorkerEntity workerEntity = workerRepository.findById(workerId).orElseThrow(() -> new WorkerException("The worker isn't found"));
        return mapper.toWorkerDto(workerEntity);
    }

}
