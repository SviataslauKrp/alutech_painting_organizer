package org.painting.alutechorganizer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@MappedSuperclass
public abstract class WorkerEntity {

    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    private UUID id;

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private Date startWorking;
    @NotBlank
    private Integer employeeNumber;
    @Enumerated
    @NotBlank
    private Workspaces workspace;

}
