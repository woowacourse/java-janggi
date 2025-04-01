package janggi.entity;

import java.time.LocalDateTime;

public record BoardSnapshot(
        int snapshotId,
        int gameId,
        String turn,
        LocalDateTime createdAt
) {
}
