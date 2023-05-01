package org.painting.alutechorganizer.repository;

import org.painting.alutechorganizer.domain.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Integer> {

    WorkerEntity findByNameOrSurname(String name, String Surname);

}
