package org.painting.alutechorganizer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.dto.MasterDto;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {WorkerMapper.class, WorkplaceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MasterMapper {

    @Mapping(target = "workers", ignore = true)
    MasterEntity toMasterEntity(MasterDto masterDto);

    MasterDto toMasterDto(MasterEntity masterEntity);

    List<MasterEntity> toListEntities(List<MasterDto> dtos);

    List<MasterDto> toListDtos(List<MasterEntity> entities);

    void updateMasterFromDto(MasterDto masterDto, @MappingTarget MasterEntity masterEntity);

}
