package org.painting.alutechorganizer.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @OneToOne(mappedBy = "worker")
    @Transient
    private UserEmployee user;

    private String note;

    @Column(name = "is_available",
            columnDefinition = "boolean default true")
    private Boolean isAvailable = true;

    public void leaveWorkplace() {
        workplace = null;
    }

}
