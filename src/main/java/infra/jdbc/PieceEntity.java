package infra.jdbc;

import domain.pieces.Side;
import domain.pieces.PieceType;

public record PieceEntity(
        int row,
        int column,
        Side side,
        PieceType pieceType
) {
}
