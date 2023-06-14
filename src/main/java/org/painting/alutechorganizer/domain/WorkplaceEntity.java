package org.painting.alutechorganizer.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Entity
@Table(name = "workplaces")
public class WorkplaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "workplace")
    private List<WorkerEntity> workers;

    @ManyToOne
    @NotNull
    private MasterEntity master;

    public void addWorker(WorkerEntity worker) {
        workers.add(worker);
        worker.setWorkplace(this);
    }

    public void removeWorker(WorkerEntity worker) {
        workers.remove(worker);
        worker.leaveWorkplace();
    }

}
