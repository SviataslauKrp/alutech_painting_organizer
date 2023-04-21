package org.painting.alutechorganizer.domain;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "stackers")
public class StackerEntity extends WorkerEntity {

    @ElementCollection(targetClass = Roles.class)
    private Set<Roles> roles;

    private enum Roles {
        LOADER,
        UNLOADER,
        PASTERER,
        HOOKER,
        CLOTHESPINER
    }
}
