package org.painting.alutechorganizer.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class WorkerDto {

    private Integer id;

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotNull
    private String startWorking;
    private WorkplaceDto workplace;
    @NotNull
    private String profession;
    private MasterDto master;
    private String note;
    private Boolean isAvailable;

}
