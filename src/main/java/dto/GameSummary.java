package dto;

import java.time.LocalDateTime;

public record GameSummary(long id, LocalDateTime startedAt, String currentTurn) {
}
