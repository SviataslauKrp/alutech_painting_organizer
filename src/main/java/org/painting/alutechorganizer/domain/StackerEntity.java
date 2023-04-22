package org.painting.alutechorganizer.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
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
