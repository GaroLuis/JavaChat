package io.github.garoluis.javachat.core.auth.domain;

import java.util.UUID;

public record Session(String token, UUID userId, String username) {
}
