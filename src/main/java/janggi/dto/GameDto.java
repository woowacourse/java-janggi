package janggi.dto;

import java.time.LocalDateTime;

public record GameDto(int id, String turn, LocalDateTime createdAt) {
}
