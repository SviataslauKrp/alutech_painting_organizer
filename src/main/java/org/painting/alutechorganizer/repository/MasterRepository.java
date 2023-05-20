package org.painting.alutechorganizer.repository;

import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MasterRepository extends JpaRepository<MasterEntity, Integer> {

}
