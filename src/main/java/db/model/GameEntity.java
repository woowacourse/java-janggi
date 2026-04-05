package db.model;

import core.GameStatus;
import pieces.Side;

public record GameEntity(
    Long id,
    Side turnSide,
    GameStatus status) {
}
