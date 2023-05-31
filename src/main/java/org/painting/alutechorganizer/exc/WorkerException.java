package org.painting.alutechorganizer.exc;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WorkerException extends RuntimeException {
    public WorkerException(String message) {
        super(message);
    }
}
