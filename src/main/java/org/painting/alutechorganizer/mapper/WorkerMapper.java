package org.painting.alutechorganizer.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.dto.WorkplaceDto;
import org.painting.alutechorganizer.exc.MasterException;
import org.painting.alutechorganizer.exc.WorkplaceException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface WorkerMapper {

    WorkerDto toWorkerDto(WorkerEntity entity);

    @Mapping(target = "workers", ignore = true)
    MasterDto toMasterDto(MasterEntity masterEntity);

    @Mapping(target = "workers", ignore = true)
    @Mapping(target = "master", ignore = true)
    WorkplaceDto toWorkplaceDto(WorkplaceEntity workplaceEntity);

    @Mapping(target = "startWorking", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "isAvailable", defaultValue = "true")
    WorkerEntity toWorkerEntity(WorkerDto dto);

    List<WorkerEntity> toListEntities(List<WorkerDto> dtos);

    List<WorkerDto> toListDtos(List<WorkerEntity> entities);

    void updateWorkerFromDto(WorkerDto workerDto, @MappingTarget WorkerEntity workerEntity);

}

