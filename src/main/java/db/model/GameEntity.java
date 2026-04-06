package db.model;

import core.GameStatus;
import java.time.LocalDateTime;
import pieces.Side;

public record GameEntity(
    Long id,
    String roomName,
    Side turnSide,
    GameStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}
