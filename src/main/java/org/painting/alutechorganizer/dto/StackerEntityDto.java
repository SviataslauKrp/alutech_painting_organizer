package org.painting.alutechorganizer.dto;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class StackerEntityDto extends WorkerDto {

    public StackerEntityDto(String name, String surname, Date startWorking, Integer employeeNumber) {
        super(name, surname, startWorking, employeeNumber);
        this.roles = new HashSet<>();
    }

    private Set<Roles> roles;

    private enum Roles {
        LOADER,
        UNLOADER,
        PASTERER,
        HOOKER,
        CLOTHESPINER
    }
}
