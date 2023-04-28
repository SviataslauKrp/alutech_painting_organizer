package org.painting.alutechorganizer.repository;

import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkplaceRepository extends JpaRepository<WorkplaceEntity, Integer> {

    WorkplaceEntity findByName(String name);

}
