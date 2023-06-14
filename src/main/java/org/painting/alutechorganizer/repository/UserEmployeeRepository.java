package org.painting.alutechorganizer.repository;

import org.painting.alutechorganizer.domain.UserEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEmployeeRepository extends JpaRepository<UserEmployee, Integer> {

    Optional<UserEmployee> findByUsername(String name);

    Optional<UserEmployee> findByWorkerId(Integer workerId);

    Optional<UserEmployee> findByMasterId(Integer masterId);

}
