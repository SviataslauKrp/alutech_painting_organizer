package org.painting.alutechorganizer.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.Profession;
import org.painting.alutechorganizer.domain.UserEmployee;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.exc.WorkerException;
import org.painting.alutechorganizer.mapper.WorkerMapper;
import org.painting.alutechorganizer.repository.MasterRepository;
import org.painting.alutechorganizer.repository.UserEmployeeRepository;
import org.painting.alutechorganizer.repository.WorkerRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkerServiceImplTest {

    @Mock
    private WorkerRepository workerRepository;
    @Mock
    private WorkerMapper mapper;
    @Mock
    UserEmployeeRepository userRepository;
    @Mock
    MasterRepository masterRepository;
    @InjectMocks
    private WorkerServiceImpl service;

    private WorkerDto testWorkerDto;
    private WorkerEntity testWorkerEntity;
    private MasterEntity testMaster;
    private UserEmployee testUser;

    @BeforeEach
    void init() {

        MockitoAnnotations.openMocks(this);

        testWorkerDto = WorkerDto.builder()
                .id(1)
                .name("testName")
                .surname("testSurname")
                .startWorking("2000-01-01")
                .profession("CORRECTOR")
                .build();
        testWorkerEntity = WorkerEntity.builder()
                .id(1)
                .name("testName")
                .surname("testSurname")
                .startWorking(LocalDate.of(2000, 1, 1))
                .profession(Profession.CORRECTOR)
                .build();
        testMaster = MasterEntity.builder()
                .id(1)
                .workers(new ArrayList<>())
                .build();
        testUser = UserEmployee.builder()
                .id(1)
                .username("user@gmail.com")
                .password("1234")
                .build();
        testWorkerEntity.setUser(testUser);
        testUser.setWorker(testWorkerEntity);
        testMaster.addWorker(testWorkerEntity);
    }

    @Test
    void testGetAllWorkers() {

        //given
        when(workerRepository.findAll()).thenReturn(List.of(testWorkerEntity));
        when(mapper.toListDtos(any())).thenReturn(List.of(testWorkerDto));

        //when
        List<WorkerDto> allWorkers = service.getAllWorkers();

        //then
        assertEquals(1, allWorkers.size());
        assertEquals(testWorkerDto, allWorkers.get(0));

    }

    @Test
    void getWorkerBySurnameAndMasterId() {

        //given
        Integer masterId = testWorkerEntity.getMaster().getId();
        String surname = testWorkerEntity.getSurname();
        when(workerRepository.findBySurnameAndMasterId(surname, masterId)).thenReturn(Optional.of(List.of(testWorkerEntity)));

        //when
        service.getWorkerBySurnameAndMasterId(surname, masterId);

        //then
        verify(workerRepository, times(1)).findBySurnameAndMasterId(surname, masterId);
        assertEquals(surname, testWorkerEntity.getSurname());
        assertEquals(masterId, testWorkerEntity.getMaster().getId());

    }

    @Test
    void getWorkerBySurnameAndMasterIdWithWorkerException() {

        //given
        Integer masterId = testWorkerEntity.getMaster().getId();
        String surname = testWorkerEntity.getSurname();
        when(workerRepository.findBySurnameAndMasterId(surname, masterId)).thenReturn(Optional.empty());

        //when and then
        assertThrows(WorkerException.class, () -> service.getWorkerBySurnameAndMasterId(surname, masterId));
        verify(mapper, never()).toWorkerDto(any());
    }

    @Test
    void testDeleteWorkerById() {

        //given
        Integer workerId = testWorkerEntity.getId();
        when(workerRepository.findById(workerId)).thenReturn(Optional.of(testWorkerEntity));

        //when
        service.deleteWorkerById(workerId);

        //then
        verify(userRepository, times(1)).delete(testUser);

    }

    @Test
    void testUpdateWorker() {

        //given
        WorkerDto updatedDto = WorkerDto.builder()
                .name("newName")
                .surname("newSurname")
                .isAvailable(true)
                .build();
        WorkerEntity updatedEntity = WorkerEntity.builder()
                .name("newName")
                .surname("newSurname")
                .isAvailable(true)
                .build();

        Integer id = testWorkerEntity.getId();
        ArgumentCaptor<WorkerEntity> workerEntityArgumentCaptor = ArgumentCaptor.forClass(WorkerEntity.class);

        when(workerRepository.findById(id)).thenReturn(Optional.of(updatedEntity));

        //when
        service.updateWorker(updatedDto, id);

        //then
        verify(workerRepository, times(1)).findById(id);
        verify(mapper, times(1)).updateWorkerFromDto(eq(updatedDto), workerEntityArgumentCaptor.capture());

        WorkerEntity value = workerEntityArgumentCaptor.getValue();

        assertEquals(updatedEntity.getName(), value.getName());
        assertEquals(updatedEntity.getSurname(), value.getSurname());

    }

    @Test
    void testGetWorkerById() {

        //given
        Integer id = testWorkerEntity.getId();
        when(workerRepository.findById(id)).thenReturn(Optional.of(testWorkerEntity));
        when(mapper.toWorkerDto(any(WorkerEntity.class))).thenReturn(testWorkerDto);

        //when
        WorkerDto worker = service.getWorkerById(id);

        //then
        assertEquals(testWorkerDto, worker);
    }

    @Test
    void testGetWorkersByMasterId() {
        //given
        Integer masterId = testMaster.getId();
        when(workerRepository.findByMasterId(masterId)).thenReturn(List.of(testWorkerEntity));
        when(mapper.toListDtos(List.of(testWorkerEntity))).thenReturn(List.of(testWorkerDto));

        //when
        List<WorkerDto> workers = service.getWorkersByMasterId(masterId);

        //then
        verify(mapper, times(1)).toListDtos(List.of(testWorkerEntity));
        assertEquals(1, workers.size());
        assertEquals(testWorkerDto, workers.get(0));
    }

    @Test
    void setToMaster() {
        //given
        when(workerRepository.findById(testWorkerEntity.getId())).thenReturn(Optional.of(testWorkerEntity));
        when(masterRepository.findById(testMaster.getId())).thenReturn(Optional.of(testMaster));

        //when
        testMaster.addWorker(testWorkerEntity);

        //then
        assertTrue(testMaster.getWorkers().contains(testWorkerEntity));
        assertEquals(testWorkerEntity.getMaster(), testMaster);
    }

}