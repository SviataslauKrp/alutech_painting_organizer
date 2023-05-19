package org.painting.alutechorganizer.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
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

    @ManyToOne
    private WorkplaceEntity workplace;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Profession profession;

    @ManyToOne
    @NotNull
    private MasterEntity master;

    public boolean isAvailable() {
        return workplace == null;
    }

    public void leaveWorkplace() {
        workplace = null;
    }
    public void getAwayFromMaster() {
        master = null;
    }

}
