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

    @OneToMany(mappedBy = "master",
            fetch = FetchType.EAGER)
    private List<WorkerEntity> workers;

    @ManyToMany
    private List<WorkplaceEntity> workplaces;


    public void addWorker(WorkerEntity worker) {

        workers.add(worker);
        worker.setMaster(this);

    }

    public void removeWorker(WorkerEntity worker) {

        workers.remove(worker);
        worker.getAwayFromMaster();

    }
}
