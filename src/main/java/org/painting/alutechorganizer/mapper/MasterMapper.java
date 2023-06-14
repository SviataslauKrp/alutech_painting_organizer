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
public interface MasterMapper {


    MasterEntity toMasterEntity(MasterDto masterDto);

    MasterDto toMasterDto(MasterEntity masterEntity);

    @Mapping(target = "master", ignore = true)
    @Mapping(target = "workplace", ignore = true)
    WorkerDto toWorkerDto(WorkerEntity workerEntity);

    @Mapping(target = "master", ignore = true)
    WorkplaceDto toWorkplaceDto(WorkplaceEntity workplaceEntity);

    List<MasterDto> toListDtos(List<MasterEntity> entities);

    @Mapping(target = "user", ignore = true)
    void updateMasterFromDto(MasterDto masterDto, @MappingTarget MasterEntity masterEntity);

}
