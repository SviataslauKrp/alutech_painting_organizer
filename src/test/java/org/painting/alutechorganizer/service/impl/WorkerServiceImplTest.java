//package org.painting.alutechorganizer.service.impl;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.painting.alutechorganizer.domain.Profession;
//import org.painting.alutechorganizer.domain.WorkerEntity;
//import org.painting.alutechorganizer.dto.WorkerDto;
//import org.painting.alutechorganizer.exc.WorkerNotFoundException;
//import org.painting.alutechorganizer.mapper.WorkerMapper;
//import org.painting.alutechorganizer.repository.WorkerRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class WorkerServiceImplTest {
//
//    @Mock
//    private WorkerRepository repository;
//    @Mock
//    private WorkerMapper mapper;
//    @InjectMocks
//    private WorkerServiceImpl service;
//
//    private WorkerDto testWorkerDto;
//    private WorkerEntity testWorkerEntity;
//
//    @BeforeEach
//    void init() {
//
//        MockitoAnnotations.openMocks(this);
//
//        testWorkerDto = WorkerDto.builder()
//                .id(1)
//                .name("testName")
//                .surname("testSurname")
//                .startWorking("2000-01-01")
//                .profession("CORRECTOR")
//                .build();
//        testWorkerEntity = WorkerEntity.builder()
//                .id(1)
//                .name("testName")
//                .surname("testSurname")
//                .startWorking(LocalDate.of(2000, 1, 1))
//                .profession(Profession.CORRECTOR)
//                .build();
//    }
//
//
//    @Test
//    void testSaveWorker() {
//
//        //given
//        when(mapper.toWorkerEntity(any(WorkerDto.class))).thenReturn(testWorkerEntity);
//
//        //when
//        service.saveWorker(testWorkerDto, any());
//
//        //then
//        verify(mapper, times(1)).toWorkerEntity(any(WorkerDto.class));
//        verify(repository, times(1)).save(any(WorkerEntity.class));
//    }
//
//    @Test
//    void testGetAllWorkers() {
//
//        //given
//        when(repository.findAll()).thenReturn(List.of(testWorkerEntity));
//        when(mapper.toListDtos(any())).thenReturn(List.of(testWorkerDto));
//
//        //when
//        List<WorkerDto> allWorkers = service.getAllWorkers();
//
//        //then
//        assertEquals(1, allWorkers.size());
//        assertEquals(testWorkerDto, allWorkers.get(0));
//
//    }
//
//    @Test
//    void testGetAllWorkersWithEmptyBrigadeException() {
//
//        //given
//        when(repository.findAll()).thenReturn(List.of());
//        when(mapper.toListDtos(any())).thenReturn(List.of());
//
//        //when
//        List<WorkerDto> allWorkers = service.getAllWorkers();
//
//        //then
//        assertEquals(0, allWorkers.size());
//    }
//
//    @Test
//    void testGetWorkerById() {
//
//        //given
//        Integer id = testWorkerEntity.getId();
//        when(repository.findById(id)).thenReturn(Optional.of(testWorkerEntity));
//        when(mapper.toWorkerDto(any(WorkerEntity.class))).thenReturn(testWorkerDto);
//
//        //when
//        WorkerDto worker = service.getWorkerById(id);
//
//        //then
//        assertEquals(testWorkerDto, worker);
//    }
//
//    @Test
//    void testGetWorkerByIdWithWorkerNotFoundException() {
//
//        //given
//        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
//
//        //when and then
//        assertThrows(WorkerNotFoundException.class, () -> service.getWorkerById(1));
//
//    }
//
//
//    @Test
//    void testDeleteWorkerById() {
//
//        //given
//        Integer id = testWorkerEntity.getId();
//
//        //when
//        service.deleteWorkerById(id);
//
//        //then
//        verify(repository, times(1)).deleteById(id);
//
//    }
//
//    @Test
//    void testUpdateWorker() {
//
//        //given
//        WorkerDto updatedDto = WorkerDto.builder()
//                .name("newName")
//                .surname("newSurname")
//                .build();
//        WorkerEntity updatedEntity = WorkerEntity.builder()
//                .name("newName")
//                .surname("newSurname")
//                .build();
//
//        Integer id = testWorkerEntity.getId();
//        ArgumentCaptor<WorkerEntity> workerEntityArgumentCaptor = ArgumentCaptor.forClass(WorkerEntity.class);
//
//        when(repository.findById(id)).thenReturn(Optional.of(updatedEntity));
//
//        //when
//        service.updateWorker(updatedDto, id);
//
//        //then
//        verify(repository, times(1)).findById(id);
//        verify(mapper, times(1)).updateWorkerFromDto(eq(updatedDto), workerEntityArgumentCaptor.capture());
//
//        WorkerEntity value = workerEntityArgumentCaptor.getValue();
//
//        assertEquals(updatedEntity.getName(), value.getName());
//        assertEquals(updatedEntity.getSurname(), value.getSurname());
//
//    }
//
//    @Test
//    void testUpdateWorkerWithWorkerNotFoundException() {
//
//        //given
//        Integer id = testWorkerEntity.getId();
//        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
//
//        //when and then
//        assertThrows(WorkerNotFoundException.class, () -> service.updateWorker(any(WorkerDto.class), id));
//
//        verify(repository, times(1)).findById(id);
//        verify(mapper, never()).updateWorkerFromDto(any(WorkerDto.class), any(WorkerEntity.class));
//
//    }
//}