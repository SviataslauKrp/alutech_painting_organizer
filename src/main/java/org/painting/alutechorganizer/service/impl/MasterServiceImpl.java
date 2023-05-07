package org.painting.alutechorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.exc.EmptyMastersListException;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkerNotFoundException;
import org.painting.alutechorganizer.mapper.MasterMapper;
import org.painting.alutechorganizer.repository.MasterRepository;
import org.painting.alutechorganizer.repository.WorkerRepository;
import org.painting.alutechorganizer.service.MasterService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor

@Service
@Transactional
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;
    private final WorkerRepository workerRepository;
    private final MasterMapper masterMapper;


    @Override
    public void saveMaster(MasterDto master) {

        MasterEntity masterEntity = masterMapper.toMasterEntity(master);
        masterRepository.save(masterEntity);

    }

    @Override
    public void deleteMasterById(Integer id) {

        MasterEntity masterEntity = masterRepository.findById(id).orElseThrow(MasterException::new);
        masterEntity.getWorkers().forEach(WorkerEntity::getAwayFromMaster);
        masterRepository.deleteById(id);

    }

    @Override
    public MasterDto getMasterById(Integer id) {

        MasterEntity masterEntity = masterRepository.findById(id).orElseThrow(MasterException::new);
        return masterMapper.toMasterDto(masterEntity);

    }

    @Override
    public void updateMasterById(MasterDto masterDto, Integer id) {

        MasterEntity masterEntity = masterRepository.findById(id).orElseThrow(MasterException::new);
        masterMapper.updateMasterFromDto(masterDto, masterEntity);

    }

    @Override
    public List<MasterDto> getAllMasters() {

        List<MasterEntity> allMasterEntities = masterRepository.findAll();
        if (allMasterEntities.isEmpty()) {
            throw new EmptyMastersListException();
        }

        return masterMapper.toListDtos(allMasterEntities);

    }

    @Override
    public void addWorkerToMaster(Integer workerId, Integer masterId) {

        MasterEntity masterEntity = masterRepository.findById(masterId).orElseThrow(MasterException::new);
        WorkerEntity workerEntity = workerRepository.findById(workerId).orElseThrow(WorkerNotFoundException::new);

        masterEntity.addWorker(workerEntity);
    }

    @Override
    public void removeWorkerFromMaster(Integer workerId, Integer masterId) {

        MasterEntity masterEntity = masterRepository.findById(masterId).orElseThrow(MasterException::new);
        WorkerEntity workerEntity = workerRepository.findById(workerId).orElseThrow(WorkerNotFoundException::new);

        if (masterEntity.getWorkers().contains(workerEntity)) {
            masterEntity.removeWorker(workerEntity);
        }

    }

}
