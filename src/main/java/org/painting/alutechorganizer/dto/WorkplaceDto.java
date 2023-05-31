package org.painting.alutechorganizer.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class WorkplaceDto {

    private Integer id;
    @NotBlank
    private String name;
    private List<WorkerDto> workers;
    private MasterDto master;

}
