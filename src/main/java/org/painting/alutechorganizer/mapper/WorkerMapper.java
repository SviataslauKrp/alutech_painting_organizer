package org.painting.alutechorganizer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.painting.alutechorganizer.domain.*;
import org.painting.alutechorganizer.dto.*;

@Mapper(componentModel = "spring",
        subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface WorkerMapper {

    @SubclassMapping(source = CorrectorEntity.class, target = CorrectorDto.class)
    @SubclassMapping(source = OperatorEntity.class, target = OperatorDto.class)
    @SubclassMapping(source = SeniorWorkerEntity.class, target = SeniorWorkerDto.class)
    @SubclassMapping(source = StackerEntity.class, target = StackerEntityDto.class)
    WorkerDto toDto(WorkerEntity workerEntity);

    @SubclassMapping(source = CorrectorDto.class, target = CorrectorEntity.class)
    @SubclassMapping(source = OperatorDto.class, target = OperatorEntity.class)
    @SubclassMapping(source = SeniorWorkerDto.class, target = SeniorWorkerEntity.class)
    @SubclassMapping(source = StackerEntityDto.class, target = StackerEntity.class)
    WorkerEntity toEntity(WorkerDto workerDto);

}
