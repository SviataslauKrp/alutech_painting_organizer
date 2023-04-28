package org.painting.alutechorganizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WorkplaceDto {

    private Integer id;

    @NotBlank
    private String name;

    private List<WorkerDto> workers;

}
