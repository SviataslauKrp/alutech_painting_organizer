package org.painting.alutechorganizer.service;

import org.painting.alutechorganizer.dto.MasterDto;

import java.util.List;

public interface MasterService {
    void saveMaster(MasterDto master);

    void deleteMasterById(Integer id);

    MasterDto getMasterById(Integer id);

    void updateMasterById(MasterDto masterDto, Integer id);

    List<MasterDto> getAllMasters();

}
