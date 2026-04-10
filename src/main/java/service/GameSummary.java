package service;

import core.GameStatus;
import core.Turn;

public record GameSummary(
    Long id,
    Turn turn,
    GameStatus status) {

    public boolean isOver() {
        return status.isOver();
    }
}
