package org.painting.alutechorganizer.domain;

import lombok.*;
import org.hibernate.jdbc.Work;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
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

    @Transient
    private final Profession profession = Profession.MASTER;

    @OneToMany(mappedBy = "master",
               fetch = FetchType.EAGER)
    private List<WorkerEntity> workers;

    public void addWorker(WorkerEntity worker) {

        workers.add(worker);
        worker.setMaster(this);

    }
    public void removeWorker(WorkerEntity worker) {

        workers.remove(worker);
        worker.getAwayFromMaster();

    }
}
