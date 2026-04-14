package janggi.domain.board.strategy;

import janggi.domain.Side;
import janggi.domain.piece.PieceType;

public record PieceInfo(
        PieceType type,
        Side side,
        int rowIndex,
        int colIndex
) {
}
