package janggi.dto;

import java.time.LocalDateTime;

public record GameSummary(int id, String turn, LocalDateTime createdAt) {
}
