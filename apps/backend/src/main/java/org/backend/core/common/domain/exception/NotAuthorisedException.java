package org.backend.core.common.domain.exception;

public class NotAuthorisedException extends RuntimeException {
    public NotAuthorisedException() {
        super("Not authorised action");
    }
}
