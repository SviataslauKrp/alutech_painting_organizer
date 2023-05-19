package org.painting.alutechorganizer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.dto.WorkplaceDto;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = WorkerMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)

public interface WorkplaceMapper {


    WorkplaceEntity toWorkplaceEntity(WorkplaceDto dto);

    WorkplaceDto toWorkplaceDto(WorkplaceEntity entity);

    List<WorkplaceEntity> toListEntities(List<WorkplaceDto> dtos);

    List<WorkplaceDto> toListDtos(List<WorkplaceEntity> entities);

    void updateWorkplaceFromDto(WorkplaceDto workplaceDto, @MappingTarget WorkplaceEntity workplaceEntity);

}
