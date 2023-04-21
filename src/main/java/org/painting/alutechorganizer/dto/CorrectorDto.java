package org.painting.alutechorganizer.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.painting.alutechorganizer.domain.Workspaces;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@NoArgsConstructor
public class CorrectorDto extends WorkerDto {

    public CorrectorDto(String name, String surname, Date startWorking, Integer employeeNumber) {
        super(name, surname, startWorking, employeeNumber);
    }
}
