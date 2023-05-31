package org.painting.alutechorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkerNotFoundException;
import org.painting.alutechorganizer.mapper.MasterMapper;
import org.painting.alutechorganizer.repository.MasterRepository;
import org.painting.alutechorganizer.repository.WorkerRepository;
import org.painting.alutechorganizer.service.MasterService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RequiredArgsConstructor

@Service
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;
    private final WorkerRepository workerRepository;
    private final MasterMapper masterMapper;


    @Override
    public void saveMaster(MasterDto master) {

        MasterEntity masterEntity = masterMapper.toMasterEntity(master);
        masterRepository.save(masterEntity);

    }

    @Transactional
    @Override
    public void deleteMasterById(Integer id) {

        MasterEntity masterEntity = masterRepository.findById(id).orElseThrow(() -> new MasterException("The master isn't found"));

        if (masterEntity.getWorkers().size() != 0) {
            throw new MasterException("You need to transfer all your workers to another master");
        }

        masterRepository.deleteById(id);

    }

    @Override
    public MasterDto getMasterById(Integer id) {

        MasterEntity masterEntity = masterRepository.findById(id).orElseThrow(() -> new MasterException("The master isn't found"));
        return masterMapper.toMasterDto(masterEntity);

    }

    @Transactional
    @Override
    public void updateMasterById(MasterDto masterDto, Integer id) {

        MasterEntity masterEntity = masterRepository.findById(id).orElseThrow(() -> new MasterException("The master isn't found"));
        masterMapper.updateMasterFromDto(masterDto, masterEntity);


    }

    @Override
    public List<MasterDto> getAllMasters() {

        List<MasterEntity> allMasterEntities = masterRepository.findAll();
        return masterMapper.toListDtos(allMasterEntities);

    }

    @Transactional
    @Override
    public void addWorkerToMaster(Integer workerId, Integer masterId) {

        MasterEntity masterEntity = masterRepository.findById(masterId).orElseThrow(() -> new MasterException("The master isn't found"));
        WorkerEntity workerEntity = workerRepository.findById(workerId).orElseThrow(WorkerNotFoundException::new);

        masterEntity.addWorker(workerEntity);
    }

}
