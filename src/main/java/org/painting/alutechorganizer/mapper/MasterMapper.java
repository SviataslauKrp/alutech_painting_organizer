package org.painting.alutechorganizer.mapper;

import org.mapstruct.*;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.dto.WorkerDto;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {WorkerMapper.class, WorkplaceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface MasterMapper {

    @Mapping(target = "workers", ignore = true)
    MasterEntity toMasterEntity(MasterDto masterDto);

    MasterDto toMasterDto(MasterEntity masterEntity);

    List<MasterEntity> toListEntities(List<MasterDto> dtos);

    List<MasterDto> toListDtos(List<MasterEntity> entities);

    void updateMasterFromDto(MasterDto masterDto, @MappingTarget MasterEntity masterEntity);

}
