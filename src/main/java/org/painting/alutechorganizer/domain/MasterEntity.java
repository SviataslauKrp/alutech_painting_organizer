package org.painting.alutechorganizer.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Entity
@Table(name = "masters")
public class MasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank
    private String name;
    @NotBlank
    private String surname;

    @OneToMany(mappedBy = "master")
    private List<WorkerEntity> workers;

    @OneToMany(mappedBy = "master")
    private List<WorkplaceEntity> workplaces;

    @OneToOne(mappedBy = "master")
    @Transient
    private UserEmployee user;


    public void addWorker(WorkerEntity worker) {
        workers.add(worker);
        worker.setMaster(this);
        worker.setWorkplace(null);
    }

    public void addWorkplace(WorkplaceEntity workplace) {
        workplaces.add(workplace);
        workplace.setMaster(this);
    }
}
