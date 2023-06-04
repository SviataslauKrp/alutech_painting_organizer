package org.painting.alutechorganizer.service;

import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.exc.MasterException;

import java.util.List;

public interface MasterService {
    public void saveMaster(MasterDto master);

    void deleteMasterById(Integer id);

    MasterDto getMasterById(Integer id);

    void updateMasterById(MasterDto masterDto, Integer id);

    List<MasterDto> getAllMasters();

}
