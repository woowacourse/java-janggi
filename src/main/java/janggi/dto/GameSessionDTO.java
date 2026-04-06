package janggi.dto;

import java.time.LocalDateTime;

public record GameSessionDTO(
        long gameId,
        String choPlayerName,
        String hanPlayerName,
        String currentTurn,
        LocalDateTime createdAt
) {
}
