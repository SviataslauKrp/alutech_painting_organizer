package org.painting.alutechorganizer.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stackers")
public class Stacker extends Worker {

    @Enumerated
    @ElementCollection(targetClass = Roles.class)
    private List<Roles> roles;

    private enum Roles {
        LOADER,
        UNLOADER,
        PASTERER,
        HOOKER,
        CLOTHESPINER
    }
}
