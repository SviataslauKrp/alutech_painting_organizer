package org.painting.alutechorganizer.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.painting.alutechorganizer.dto.WorkplaceDto;
import org.painting.alutechorganizer.exc.EmptyWorkplacesListException;
import org.painting.alutechorganizer.exc.WorkerNotFoundException;
import org.painting.alutechorganizer.exc.WorkplaceException;
import org.painting.alutechorganizer.mapper.WorkplaceMapper;
import org.painting.alutechorganizer.repository.WorkerRepository;
import org.painting.alutechorganizer.repository.WorkplaceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkplaceServiceImplTest {

    @Mock
    private WorkplaceMapper workplaceMapper;
    @Mock
    private WorkplaceRepository workplaceRepository;
    @Mock
    private WorkerRepository workerRepository;
    @InjectMocks
    private WorkplaceServiceImpl service;

    private WorkplaceDto testWorkplaceDto;
    private WorkplaceEntity testWorkplaceEntity;
    private WorkerEntity workerWithWorkplace, workerWithoutWorkplace;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        testWorkplaceDto = WorkplaceDto.builder()
                .name("testName")
                .id(1)
                .workers(new ArrayList<>())
                .build();
        testWorkplaceEntity = WorkplaceEntity.builder()
                .name("testName")
                .id(1)
                .workers(new ArrayList<>())
                .build();

        workerWithWorkplace = WorkerEntity.builder()
                .id(1)
                .name("testWorkerName1")
                .surname("testWorkerSurname1")
                .workplace(testWorkplaceEntity)
                .build();
        workerWithoutWorkplace = WorkerEntity.builder()
                .id(2)
                .name("testWorkerName2")
                .surname("testWorkerSurname2")
                .build();
        testWorkplaceEntity.addWorker(workerWithWorkplace);
    }


    @Test
    void testSaveWorkplace() {

        //given
        when(workplaceMapper.toWorkplaceEntity(testWorkplaceDto)).thenReturn(testWorkplaceEntity);

        //when
        service.saveWorkplace(testWorkplaceDto, masterId);

        //then
        verify(workplaceRepository, times(1)).save(testWorkplaceEntity);
    }

    @Test
    void testGetAllWorkplaces() {

        //given
        when(workplaceRepository.findAll()).thenReturn(List.of(testWorkplaceEntity));
        when(workplaceMapper.toListDtos(any())).thenReturn(List.of(testWorkplaceDto));

        //when
        List<WorkplaceDto> allWorkplaces = service.getAllWorkplaces();

        //then
        assertEquals(1, allWorkplaces.size());
        assertEquals(testWorkplaceDto, allWorkplaces.get(0));

    }

    @Test
    void testGetAllWorkplacesWithEmptyWorkplacesListException() {

        //given
        when(workplaceRepository.findAll()).thenReturn(List.of());

        //when and then
        assertThrows(EmptyWorkplacesListException.class, () -> service.getAllWorkplaces());

    }


    @Test
    void testGetWorkplaceById() {

        //given
        Integer id = testWorkplaceEntity.getId();
        when(workplaceRepository.findById(id)).thenReturn(Optional.of(testWorkplaceEntity));
        when(workplaceMapper.toWorkplaceDto(testWorkplaceEntity)).thenReturn(testWorkplaceDto);

        //when
        WorkplaceDto workplaceById = service.getWorkplaceById(id);

        //then
        assertEquals(testWorkplaceDto, workplaceById);

    }

    @Test
    void testGetWorkplaceByIdWithWorkplaceException() {

        //given
        when(workplaceRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        //when and then
        assertThrows(WorkplaceException.class, () -> service.getWorkplaceById(any(Integer.class)));
        verify(workplaceMapper, never()).toWorkplaceDto(any(WorkplaceEntity.class));

    }

    @Test
    void testDeleteWorkplaceById() {

        //given
        Integer id = testWorkplaceEntity.getId();
        when(workplaceRepository.findById(id)).thenReturn(Optional.of(testWorkplaceEntity));

        //when
        service.deleteWorkplaceById(id);

        //then
        verify(workplaceRepository, times(1)).deleteById(id);
        verify(workplaceRepository, times(1)).findById(id);
        assertNull(workerWithWorkplace.getWorkplace());

    }

    @Test
    void testDeleteWorkplaceByIdWithWorkplaceException() {

        //given
        when(workplaceRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        //when and then
        assertThrows(WorkplaceException.class, () -> service.deleteWorkplaceById(any(Integer.class)));
    }

    @Test
    void testUpdateWorkplaceById() {

        //given
        WorkplaceEntity updatedEntity = WorkplaceEntity.builder()
                .name("newTestName")
                .build();
        WorkplaceDto updatedDto = WorkplaceDto.builder()
                .name("newTestName")
                .build();
        Integer id = testWorkplaceEntity.getId();
        when(workplaceRepository.findById(id)).thenReturn(Optional.of(updatedEntity));
        ArgumentCaptor<WorkplaceEntity> workplaceEntityArgumentCaptor = ArgumentCaptor.forClass(WorkplaceEntity.class);

        //when
        service.updateWorkplaceById(updatedDto, id);

        //then
        verify(workplaceRepository, times(1)).findById(id);
        verify(workplaceMapper, times(1)).updateWorkplaceFromDto(eq(updatedDto), workplaceEntityArgumentCaptor.capture());

        WorkplaceEntity value = workplaceEntityArgumentCaptor.getValue();

        assertEquals(updatedEntity.getName(), value.getName());

    }


    @Test
    void testAddWorkerToWorkplace() {

        //given
        Integer workplaceId = testWorkplaceEntity.getId();
        when(workplaceRepository.findById(workplaceId)).thenReturn(Optional.of(testWorkplaceEntity));
        Integer worker2Id = workerWithoutWorkplace.getId();
        when(workerRepository.findById(worker2Id)).thenReturn(Optional.of(workerWithoutWorkplace));

        //when
        service.addWorkerToWorkplace(worker2Id, workplaceId);

        //then
        assertEquals(workerWithoutWorkplace, testWorkplaceEntity.getWorkers().get(1));
        assertEquals(testWorkplaceEntity, workerWithoutWorkplace.getWorkplace());

    }

    @Test
    void testAddWorkerToWorkplaceWithWorkplaceException() {

        //given
        Integer workplaceId = testWorkplaceEntity.getId();
        when(workplaceRepository.findById(workplaceId)).thenReturn(Optional.empty());

        //when and then
        assertThrows(WorkplaceException.class, () -> service.addWorkerToWorkplace(1, 1));
    }

    @Test
    void testAddOrRemoveWorkerToWorkplaceWithWorkerNotFoundException() {

        //given
        when(workplaceRepository.findById(any(Integer.class))).thenReturn(Optional.of(testWorkplaceEntity));
        when(workerRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        //when and then
        assertThrows(WorkerNotFoundException.class, () -> service.addWorkerToWorkplace(1, 1));
    }

    @Test
    void testRemoveWorkerFromWorkplace() {

        //given
        when(workplaceRepository.findById(any(Integer.class))).thenReturn(Optional.of(testWorkplaceEntity));
        when(workerRepository.findById(any(Integer.class))).thenReturn(Optional.of(workerWithWorkplace));

        //when
        service.removeWorkerFromWorkplace(workerWithWorkplace.getId(), testWorkplaceEntity.getId());

        //then
        assertNull(workerWithWorkplace.getWorkplace());
        assertEquals(0, testWorkplaceEntity.getWorkers().size());

    }
}