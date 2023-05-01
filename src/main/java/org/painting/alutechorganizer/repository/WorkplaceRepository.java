package org.painting.alutechorganizer.repository;

import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkplaceRepository extends JpaRepository<WorkplaceEntity, Integer> {

}
