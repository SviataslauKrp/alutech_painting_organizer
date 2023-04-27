package org.painting.alutechorganizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WorkerDto {

    private Integer id;

    private String name;

    private String surname;

    private String startWorking;

    private Integer employeeNumber;

    private WorkplaceDto workplace;

    private String profession;

}