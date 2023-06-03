package org.painting.alutechorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.painting.alutechorganizer.config.SecurityConfiguration;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.Role;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.mapper.MasterMapper;
import org.painting.alutechorganizer.repository.MasterRepository;
import org.painting.alutechorganizer.repository.RoleRepository;
import org.painting.alutechorganizer.repository.UserEmployeeRepository;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MasterMapper masterMapper;
    private final MasterRepository masterRepository;

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

        user.setMaster(masterEntity);
        user.setRoles(Collections.singleton(new Role("ROLE_MASTER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean deleteUser(Integer userId) {

        Optional<UserEmployee> userFromDb = userRepository.findById(userId);
        if (userFromDb.isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
