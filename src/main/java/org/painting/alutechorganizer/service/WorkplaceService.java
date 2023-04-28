package org.painting.alutechorganizer.service;

import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.dto.WorkplaceDto;

import java.util.List;

public interface WorkplaceService {

    void saveWorkplace(WorkplaceDto workplace);

    List<WorkplaceDto> getAllWorkplaces();

    WorkplaceDto getWorkplaceById(Integer id);

    void deleteWorkplaceById(Integer id);

    void updateWorkplaceById(WorkplaceDto workplace, Integer id);

    WorkplaceDto getWorkplaceByName(String name);
}
