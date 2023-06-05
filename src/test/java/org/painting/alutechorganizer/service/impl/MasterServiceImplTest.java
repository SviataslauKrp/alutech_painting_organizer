package org.painting.alutechorganizer.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.mapper.MasterMapper;
import org.painting.alutechorganizer.repository.MasterRepository;
import org.painting.alutechorganizer.repository.UserEmployeeRepository;
import org.painting.alutechorganizer.repository.WorkerRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MasterServiceImplTest {

    @Mock
    private MasterRepository masterRepository;
    @Mock
    private MasterMapper masterMapper;
    @Mock
    private UserEmployeeRepository userRepository;

    @InjectMocks
    private MasterServiceImpl service;

    private MasterDto testMasterDto;
    private MasterEntity testMasterEntity;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        testMasterDto = MasterDto.builder()
                .id(1)
                .name("testName")
                .surname("testSurname")
                .workers(new ArrayList<>())
                .build();

        testMasterEntity = MasterEntity.builder()
                .id(1)
                .name("testName")
                .surname("testSurname")
                .workers(new ArrayList<>())
                .build();

        WorkerEntity workerWithMaster = WorkerEntity.builder()
                .id(1)
                .name("testWorkerName1")
                .surname("testWorkerSurname1")
                .build();

        UserEmployee user = UserEmployee.builder()
                .id(1)
                .username("user@gmail.com")
                .password("1234")
                .build();

        testMasterEntity.addWorker(workerWithMaster);
        user.setMaster(testMasterEntity);

    }


    @Test
    void testSaveMaster() {

        //given
        when(masterMapper.toMasterEntity(any(MasterDto.class))).thenReturn(testMasterEntity);

        //when
        service.saveMaster(testMasterDto);

        //then
        verify(masterRepository, times(1)).save(any(MasterEntity.class));
        verify(masterMapper, times(1)).toMasterEntity(any(MasterDto.class));
    }

    @Test
    void testDeleteMasterByIdWhenMasterExistWithNoWorkers() {
        // given
        Integer masterId = testMasterEntity.getId();
        when(masterRepository.findById(masterId)).thenReturn(Optional.of(testMasterEntity));
        testMasterEntity.setWorkers(new ArrayList<>());
        UserEmployee masterUser = testMasterEntity.getUser();

        // when
        service.deleteMasterById(masterId);

        // then
        verify(masterRepository, times(1)).findById(masterId);
        verify(userRepository, times(1)).delete(masterUser);
    }

    @Test
    void testDeleteMasterByIdWhenMasterExistWithWorkers() {
        // given
        Integer masterId = testMasterEntity.getId();
        when(masterRepository.findById(masterId)).thenReturn(Optional.of(testMasterEntity));

        // when and then
        assertThrows(MasterException.class, () -> service.deleteMasterById(masterId));
        verify(masterRepository, times(1)).findById(masterId);
        verify(masterRepository, times(0)).delete(any());
        verify(userRepository, times(0)).delete(any());
    }

    @Test
    void testDeleteMasterByIdWithMasterException() {

        //given
        Integer id = testMasterEntity.getId();
        when(masterRepository.findById(id)).thenReturn(Optional.empty());

        //when and then
        assertThrows(MasterException.class, () -> service.deleteMasterById(id));

    }

    @Test
    void testGetMasterById() {

        //given
        Integer id = testMasterEntity.getId();
        when(masterRepository.findById(id)).thenReturn(Optional.of(testMasterEntity));
        when(masterMapper.toMasterDto(testMasterEntity)).thenReturn(testMasterDto);

        //when
        MasterDto masterById = service.getMasterById(id);

        //then
        assertEquals(testMasterDto, masterById);

    }

    @Test
    void testGetMasterByIdWithMasterException() {

        //given
        when(masterRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        //when and then
        assertThrows(MasterException.class, () -> service.getMasterById(1));
        verify(masterMapper, never()).toMasterDto(any(MasterEntity.class));

    }

    @Test
    void testUpdateMasterById() {

        //given
        MasterEntity updatedMasterEntity = MasterEntity.builder()
                .name("newName")
                .surname("newSurname")
                .build();
        MasterDto updatedMasterDto = MasterDto.builder()
                .name("newName")
                .surname("newSurname")
                .build();

        Integer id = testMasterEntity.getId();
        ArgumentCaptor<MasterEntity> masterEntityArgumentCaptor = ArgumentCaptor.forClass(MasterEntity.class);

        when(masterRepository.findById(id)).thenReturn(Optional.of(updatedMasterEntity));

        //when
        service.updateMasterById(updatedMasterDto, id);

        //then
        verify(masterRepository, times(1)).findById(id);
        verify(masterMapper, times(1)).updateMasterFromDto(eq(updatedMasterDto), masterEntityArgumentCaptor.capture());

        MasterEntity value = masterEntityArgumentCaptor.getValue();

        assertEquals(updatedMasterEntity.getName(), value.getName());
        assertEquals(updatedMasterEntity.getSurname(), value.getSurname());

    }

    @Test
    void testUpdateMasterByIdWithMasterException() {

        //given
        Integer id = testMasterEntity.getId();
        when(masterRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        //when and then
        assertThrows(MasterException.class, () -> service.updateMasterById(any(MasterDto.class), id));

    }

    @Test
    void testGetAllMasters() {

        //given
        when(masterRepository.findAll()).thenReturn(List.of(testMasterEntity));
        when(masterMapper.toListDtos(any())).thenReturn(List.of(testMasterDto));

        //when
        List<MasterDto> allMasters = service.getAllMasters();

        //then
        assertEquals(1, allMasters.size());
        assertEquals(testMasterDto, allMasters.get(0));

    }


}