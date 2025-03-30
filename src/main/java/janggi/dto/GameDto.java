package janggi.dto;

import janggi.game.Team;
import java.time.LocalDateTime;

public record GameDto(int id, Team turn, LocalDateTime createdAt) {
}
