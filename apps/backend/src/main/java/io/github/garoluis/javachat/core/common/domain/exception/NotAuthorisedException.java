package io.github.garoluis.javachat.core.common.domain.exception;

public class NotAuthorisedException extends RuntimeException {
    public NotAuthorisedException() {
        super("Not authorised action");
    }
}
