package org.painting.alutechorganizer.service;

import org.painting.alutechorganizer.dto.WorkplaceDto;

import java.util.List;

public interface WorkplaceService {

    void saveWorkplace(WorkplaceDto workplace, Integer masterId);

    List<WorkplaceDto> getAllWorkplaces();

    WorkplaceDto getWorkplaceById(Integer id);

    void deleteWorkplaceById(Integer id);

    void updateWorkplaceById(WorkplaceDto workplace, Integer id);

    void addWorkerToWorkplace(Integer workerId, Integer workplaceId);

    void removeWorkerFromWorkplace(Integer workerId, Integer workplaceId);

    List<WorkplaceDto> getWorkplacesByMasterId(Integer masterId);
}
