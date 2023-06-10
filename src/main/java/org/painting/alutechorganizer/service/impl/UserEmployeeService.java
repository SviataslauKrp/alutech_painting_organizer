package org.painting.alutechorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.Role;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.mapper.MasterMapper;
import org.painting.alutechorganizer.repository.UserEmployeeRepository;
import org.painting.alutechorganizer.service.WorkerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor

@Service
public class UserEmployeeService implements UserDetailsService {

    private final UserEmployeeRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MasterMapper masterMapper;
    private final WorkerService workerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("The user hasn't been found"));
    }

    @Transactional
    public boolean saveUser(UserEmployee user, MasterDto masterDto) {
        Optional<UserEmployee> userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb.isPresent()) {
            return false;
        }
        MasterEntity masterEntity = masterMapper.toMasterEntity(masterDto);
        Role role = new Role("ROLE_MASTER");
        role.setUser(user);
        user.setMaster(masterEntity);
        user.setRoles(Collections.singleton(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean saveUser(UserEmployee user, WorkerDto workerDto, Integer masterId) {

        Optional<UserEmployee> userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb.isPresent()) {
            return false;
        }
        Role role = new Role("ROLE_WORKER");
        role.setUser(user);
        WorkerEntity workerEntity = workerService.setToMaster(workerDto, masterId);
        user.setWorker(workerEntity);
        user.setRoles(Collections.singleton(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public void updateUser(UserEmployee newUser) {
        UserEmployee oldUser = (UserEmployee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (StringUtils.isNotBlank(newUser.getPassword()) && !StringUtils.equals(newUser.getPassword(), oldUser.getPassword())) {
            oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(oldUser);
        }
    }

}
