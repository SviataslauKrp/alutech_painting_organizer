package org.painting.alutechorganizer.domain.worker;

import org.painting.alutechorganizer.domain.workshop.Workshop;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "workers")
public class Stacker extends Worker {

    @Column
    @Enumerated
    @ElementCollection(targetClass = Role.class)
    private List<Role> roles;

    @ManyToOne
    @JoinColumn(name = "workshop_id")
    private Workshop workshop;

    private enum Role {
        LOADER,
        UNLOADER,
        PASTERER,
        HOOKER
    }
}
