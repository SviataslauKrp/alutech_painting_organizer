package org.painting.alutechorganizer.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.painting.alutechorganizer.domain.MasterEntity;
import org.painting.alutechorganizer.domain.WorkerEntity;
import org.painting.alutechorganizer.domain.WorkplaceEntity;
import org.painting.alutechorganizer.dto.MasterDto;
import org.painting.alutechorganizer.dto.WorkerDto;
import org.painting.alutechorganizer.dto.WorkplaceDto;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {MasterMapper.class, WorkplaceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WorkerMapper {

    @Mapping(source = "master", target = "master", qualifiedByName = "toMasterDto")
    @Mapping(source = "workplace", target = "workplace", qualifiedByName = "toWorkplaceDto")
    WorkerDto toWorkerDto(WorkerEntity entity);

    @Mapping(target = "startWorking", dateFormat = "yyyy-MM-dd")
    WorkerEntity toWorkerEntity(WorkerDto dto);

    List<WorkerEntity> toListEntities(List<WorkerDto> dtos);

    List<WorkerDto> toListDtos(List<WorkerEntity> entities);

    void updateWorkerFromDto(WorkerDto workerDto, @MappingTarget WorkerEntity workerEntity);

    @Named("toMasterDto")
    default MasterDto toMasterDto(MasterEntity masterEntity) {
        if (masterEntity == null) {
            return null;
        }

        return MasterDto.builder()
                .id(masterEntity.getId())
                .name(masterEntity.getName())
                .surname(masterEntity.getSurname())
                .build();
    }

    @Named("toWorkplaceDto")
    default WorkplaceDto toWorkplaceDto(WorkplaceEntity workplaceEntity) {
        if (workplaceEntity == null) {
            return null;
        }

        return WorkplaceDto.builder()
                .id(workplaceEntity.getId())
                .name(workplaceEntity.getName())
                .build();
    }

}

