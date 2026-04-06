package core;

import participant.Turn;

public record GameSummary(
    Long id,
    Turn turn,
    GameStatus status) {
}
