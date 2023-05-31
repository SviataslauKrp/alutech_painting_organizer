package org.painting.alutechorganizer.exc;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WorkplaceException extends RuntimeException {
    String message;

    public WorkplaceException(String message) {
        super(message);
    }
}
