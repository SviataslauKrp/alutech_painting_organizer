package org.painting.alutechorganizer.repository;

import org.painting.alutechorganizer.domain.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkerRepository extends JpaRepository<WorkerEntity, UUID> {


}
