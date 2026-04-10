package janggi.dto;

import java.time.LocalDateTime;

public record GameSessionDto(
        long gameId,
        String choPlayerName,
        String hanPlayerName,
        String currentTurn,
        LocalDateTime createdAt
) {
}
