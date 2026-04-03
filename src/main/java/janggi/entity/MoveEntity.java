package janggi.entity;

import janggi.domain.side.Side;

public record MoveEntity(
        int id,
        int gameId,
        Side side,
        int fromX,
        int fromY,
        int toX,
        int toY
) {
}
