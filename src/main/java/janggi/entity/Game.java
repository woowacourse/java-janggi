package janggi.entity;

import java.time.LocalDateTime;

public record Game(
        int gameId,
        LocalDateTime createdAt,
        LocalDateTime deletedAt
) {
}