package org.painting.alutechorganizer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.painting.alutechorganizer.domain.Workspaces;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor

public abstract class WorkerDto {

    public WorkerDto(String name, String surname, Date startWorking, Integer employeeNumber) {
        this.name = name;
        this.surname = surname;
        this.startWorking = startWorking;
        this.employeeNumber = employeeNumber;
    }

    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private Date startWorking;
    @NotBlank
    private Integer employeeNumber;
    private Workspaces workspace;

}
