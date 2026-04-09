package core;

import participant.Turn;

public record GameSummary(
    Long id,
    Turn turn,
    GameStatus status) {

    public boolean isOver() {
        return status.isOver();
    }
}
