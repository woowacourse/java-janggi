package janggi.repository.dto;

import java.time.LocalDateTime;

public record GameDto(
        int id,
        String status,
        int turn,
        int choScore,
        int hanScore,
        LocalDateTime startAt,
        LocalDateTime lastSavedAt
) {
}
