package org.painting.alutechorganizer.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.mapper.MasterMapper;
import org.painting.alutechorganizer.repository.UserEmployeeRepository;
import org.painting.alutechorganizer.service.WorkerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserEmployeeServiceTest {

    @Mock
    private UserEmployeeRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MasterMapper masterMapper;
    @Mock
    private WorkerService workerService;
    @InjectMocks
    UserEmployeeService service;

    private UserEmployee testUser;
    private MasterEntity testMasterEntity;
    private MasterDto testMasterDto;
    private WorkerEntity testWorkerEntity;
    private WorkerDto testWorkerDto;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        testUser = UserEmployee.builder()
                .id(1)
                .username("testUser")
                .password("password")
                .build();
        testMasterEntity = MasterEntity.builder()
                .id(1)
                .workers(new ArrayList<>())
                .build();
        testMasterDto = new MasterDto();
        testWorkerEntity = new WorkerEntity();
        testWorkerDto = new WorkerDto();

        testMasterEntity.addWorker(testWorkerEntity);
        testUser.setMaster(testMasterEntity);
    }

    @Test
    void loadUserByUsername() {
        //given
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        //when
        UserEmployee found = (UserEmployee) service.loadUserByUsername(testUser.getUsername());

        //then
        verify(userRepository, times(1)).findByUsername(anyString());
        assertEquals(testUser, found);
    }

    @Test
    void testLoadUserByUsernameWithUserException() {
        //given
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(null);

        //when and then
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(anyString()));
    }

    @Test
    void testSaveMasterUser() {
        //given
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.empty());
        when(masterMapper.toMasterDto(testMasterEntity)).thenReturn(testMasterDto);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        //when
        boolean isSaved = service.saveUser(testUser, testMasterDto);

        //then
        assertTrue(isSaved);
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(masterMapper, times(1)).toMasterEntity(testMasterDto);
        verify(userRepository, times(1)).save(testUser);
        assertEquals("encodedPassword", passwordEncoder.encode(testUser.getPassword()));
    }


    @Test
    void testSaveExistingUser() {
        //given
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        //when
        boolean isSaved = service.saveUser(testUser, testMasterDto);

        //then
        assertFalse(isSaved);
        verify(userRepository, times(1)).findByUsername(testUser.getUsername());
        verifyNoMoreInteractions(userRepository, passwordEncoder, masterMapper);
    }

    @Test
    void testSaveWorkerUser() {
        //given
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.empty());
        when(workerService.setToMaster(testWorkerDto, testMasterEntity.getId())).thenReturn(testWorkerEntity);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        //when
        boolean isSaved = service.saveUser(testUser, testWorkerDto, testMasterEntity.getId());

        //then
        assertTrue(isSaved);
        assertEquals("encodedPassword", passwordEncoder.encode(testUser.getPassword()));
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(workerService, times(1)).setToMaster(testWorkerDto, testMasterEntity.getId());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testUpdateUser() {
        //given
        UserEmployee newUser = UserEmployee.builder()
                .username("newTestUser")
                .password("newPassword")
                .build();
        Authentication authentication = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);
        SecurityContextHolder.setContext(context);

        when(context.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        //when
        service.updateUser(newUser);

        //then
        verify(userRepository, times(1)).save(testUser);
        verify(passwordEncoder).encode(newUser.getPassword());
        assertEquals("encodedPassword", testUser.getPassword());
    }

    @Test
    void testUpdateUserWhenNewPasswordIsBlank() {
        //given
        UserEmployee newUser = UserEmployee.builder()
                .username("newTestUser")
                .password("")
                .build();
        Authentication authentication = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);
        SecurityContextHolder.setContext(context);

        when(context.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);

        //when
        service.updateUser(newUser);

        //then
        assertEquals("password", testUser.getPassword());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void testUpdateUserWhenNewPasswordIsTheSame() {
        //given
        UserEmployee newUser = UserEmployee.builder()
                .username("newTestUser")
                .password("password")
                .build();
        Authentication authentication = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);
        SecurityContextHolder.setContext(context);

        when(context.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);

        //when
        service.updateUser(newUser);

        //then
        assertEquals("password", testUser.getPassword());
        verify(passwordEncoder, never()).encode(anyString());
    }
}