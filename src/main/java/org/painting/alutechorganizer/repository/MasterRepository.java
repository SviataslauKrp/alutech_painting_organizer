package org.painting.alutechorganizer.repository;

import org.painting.alutechorganizer.domain.MasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MasterRepository extends JpaRepository<MasterEntity, Integer> {

}
