package org.painting.alutechorganizer.exc;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MasterException extends RuntimeException {
    public MasterException(String message) {
        super(message);
    }
}
