package org.painting.alutechorganizer.dto;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
public class SeniorWorkerDto extends WorkerDto {

    public SeniorWorkerDto(String name, String surname, String startWorking, Integer employeeNumber) {
        super(name, surname, startWorking, employeeNumber);
    }

}
