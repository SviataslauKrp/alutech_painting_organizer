package org.painting.alutechorganizer.repository;

import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkplaceRepository extends JpaRepository<WorkplaceEntity, Integer> {
    List<WorkplaceEntity> findByMasterId(Integer masterId);
}
