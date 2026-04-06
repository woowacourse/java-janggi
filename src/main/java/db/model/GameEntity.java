package db.model;

import core.GameStatus;
import participant.Turn;

public record GameEntity(
    Long id,
    Turn turn,
    GameStatus status) {
}
