package org.painting.alutechorganizer.repository;

import org.painting.alutechorganizer.domain.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Integer> {

    List<WorkerEntity> findByMasterId(Integer id);
    Optional<List<WorkerEntity>> findBySurnameAndMasterId(String surname, Integer masterId);
}
