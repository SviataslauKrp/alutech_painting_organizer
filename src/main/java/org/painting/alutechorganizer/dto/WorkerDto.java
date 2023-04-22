package org.painting.alutechorganizer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.painting.alutechorganizer.domain.Workspaces;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor

public abstract class WorkerDto {

    public WorkerDto(String name, String surname, String startWorking, Integer employeeNumber) {
        this.name = name;
        this.surname = surname;
        this.startWorking = startWorking;
        this.employeeNumber = employeeNumber;
    }

    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String startWorking;
    @NotNull
    private Integer employeeNumber;
    private Workspaces workspace;

}
