package org.painting.alutechorganizer.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.painting.alutechorganizer.dto.WorkplaceDto;
import org.painting.alutechorganizer.exc.EmptyWorkplacesListException;
import org.painting.alutechorganizer.exc.WorkplaceException;
import org.painting.alutechorganizer.mapper.WorkplaceMapper;
import org.painting.alutechorganizer.repository.WorkerRepository;
import org.painting.alutechorganizer.repository.WorkplaceRepository;

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

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        testWorkplaceDto = WorkplaceDto.builder()
                .name("testName")
                .id(1)
                .build();
        testWorkplaceEntity = WorkplaceEntity.builder()
                .name("testName")
                .id(1)
                .build();
    }


    @Test
    void testSaveWorkplace() {

        //given
        when(workplaceMapper.toWorkplaceEntity(testWorkplaceDto)).thenReturn(testWorkplaceEntity);

        //when
        service.saveWorkplace(testWorkplaceDto);

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
}