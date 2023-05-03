package org.painting.alutechorganizer.domain;

import lombok.*;
import org.hibernate.jdbc.Work;
import org.painting.alutechorganizer.exc.WorkerNotFoundException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder

@Entity
@Table(name = "workplaces")
public class WorkplaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "workplace",
               fetch = FetchType.EAGER)
    private List<WorkerEntity> workers;

    public void addWorker(WorkerEntity worker) {

        workers.add(worker);
        worker.setWorkplace(this);

    }

    public void removeWorker(WorkerEntity worker) {

        workers.remove(worker);
        worker.leaveWorkplace();

    }

}
