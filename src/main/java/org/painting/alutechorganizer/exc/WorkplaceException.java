package org.painting.alutechorganizer.exc;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WorkplaceException extends RuntimeException {

    public WorkplaceException(String message) {
        super(message);
    }
}
