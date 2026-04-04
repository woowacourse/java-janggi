package db.model;

import core.GameStatus;
import pieces.Side;

public record Game(
    Long id,
    Side turnSide,
    GameStatus status) {
}
