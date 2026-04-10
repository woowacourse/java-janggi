package janggi.dao.entity;

import janggi.domain.piece.PieceType;
import janggi.domain.side.Side;

public record MoveEntity(
        Integer id,
        Integer gameId,
        PieceType pieceType,
        Side side,
        int fromX,
        int fromY,
        int toX,
        int toY
) {
}
