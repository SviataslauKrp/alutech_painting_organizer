package org.painting.alutechorganizer.mapper;

import jdk.jfr.Name;
import org.mapstruct.*;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.dto.WorkplaceDto;
import org.painting.alutechorganizer.exc.MasterException;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {WorkerMapper.class, MasterMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)

public interface WorkplaceMapper {


    WorkplaceEntity toWorkplaceEntity(WorkplaceDto dto);

    @Mapping(source = "master", target = "master", qualifiedByName = "toMasterDtoIntoWorkplaceMapper")
    WorkplaceDto toWorkplaceDto(WorkplaceEntity entity);

    List<WorkplaceEntity> toListEntities(List<WorkplaceDto> dtos);

    List<WorkplaceDto> toListDtos(List<WorkplaceEntity> entities);

    void updateWorkplaceFromDto(WorkplaceDto workplaceDto, @MappingTarget WorkplaceEntity workplaceEntity);

    @Named("toMasterDtoIntoWorkplaceMapper")
    default MasterDto toMasterDto(MasterEntity entity) {
        if (entity == null) {
            throw new MasterException();
        }

        return MasterDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .build();
    }

}
