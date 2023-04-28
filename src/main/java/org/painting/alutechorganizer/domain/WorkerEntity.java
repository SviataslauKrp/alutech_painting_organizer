package org.painting.alutechorganizer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "workers")
public class WorkerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotNull
    private LocalDate startWorking;

    @ManyToOne(fetch = FetchType.EAGER)
    private WorkplaceEntity workplace;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Profession profession;

}
