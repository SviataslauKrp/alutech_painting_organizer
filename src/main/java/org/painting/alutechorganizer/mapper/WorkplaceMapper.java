package org.painting.alutechorganizer.mapper;

import org.mapstruct.*;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.dto.WorkplaceDto;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)

public interface WorkplaceMapper {

    WorkplaceEntity toWorkplaceEntity(WorkplaceDto dto);

    WorkplaceDto toWorkplaceDto(WorkplaceEntity entity);

    @Mapping(target = "workplaces", ignore = true)
    @Mapping(target = "workers", ignore = true)
    MasterDto toMasterDto(MasterEntity masterEntity);

    @Mapping(target = "workplace", ignore = true)
    WorkerDto toWorkerDto(WorkerEntity workerEntity);

    List<WorkplaceEntity> toListEntities(List<WorkplaceDto> dtos);

    List<WorkplaceDto> toListDtos(List<WorkplaceEntity> entities);

    void updateWorkplaceFromDto(WorkplaceDto workplaceDto, @MappingTarget WorkplaceEntity workplaceEntity);
}
