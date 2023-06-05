package org.painting.alutechorganizer.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MasterDto {

    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    private List<WorkerDto> workers;

    private List<WorkplaceDto> workplaces;

}
