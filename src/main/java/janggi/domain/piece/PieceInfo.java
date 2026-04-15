package janggi.domain.piece;

import janggi.domain.Side;

public record PieceInfo(
        PieceType type,
        Side side,
        int rowIndex,
        int colIndex
) {
}
