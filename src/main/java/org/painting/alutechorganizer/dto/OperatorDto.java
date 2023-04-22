package org.painting.alutechorganizer.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;


@NoArgsConstructor
public class OperatorDto extends WorkerDto {

    public OperatorDto(String name, String surname, String startWorking, Integer employeeNumber) {
        super(name, surname, startWorking, employeeNumber);
    }
}
