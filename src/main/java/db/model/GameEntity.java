package db.model;

import core.GameStatus;
import core.Turn;

public record GameEntity(
    Long id,
    Turn turn,
    GameStatus status) {
}
