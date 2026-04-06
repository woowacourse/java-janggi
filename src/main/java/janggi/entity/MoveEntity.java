package janggi.entity;

import janggi.domain.side.Side;

public record MoveEntity(
        Integer id,
        Integer gameId,
        int moveNumber,
        Side side,
        int fromX,
        int fromY,
        int toX,
        int toY
) {
}
