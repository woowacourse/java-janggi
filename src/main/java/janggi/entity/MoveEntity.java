package janggi.entity;

import janggi.domain.side.Side;

public record MoveEntity(
        Integer id,
        Integer gameId,
        Side side,
        int fromX,
        int fromY,
        int toX,
        int toY
) {
}
