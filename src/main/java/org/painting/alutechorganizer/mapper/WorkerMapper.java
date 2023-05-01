package org.painting.alutechorganizer.mapper;

import org.mapstruct.*;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.WorkerDto;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {MasterMapper.class, WorkplaceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WorkerMapper {

    WorkerDto toWorkerDto(WorkerEntity entity);

    @Mapping(target = "startWorking", dateFormat = "yyyy-MM-dd")
    WorkerEntity toWorkerEntity(WorkerDto dto);

    List<WorkerEntity> toListEntities(List<WorkerDto> dtos);

    List<WorkerDto> toListDtos(List<WorkerEntity> entities);

    void updateWorkerFromDto(WorkerDto workerDto, @MappingTarget WorkerEntity workerEntity);

}

