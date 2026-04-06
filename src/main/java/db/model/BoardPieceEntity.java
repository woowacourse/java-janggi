package db.model;

import pieces.PieceType;
import pieces.Side;

public record BoardPieceEntity(
    Long id,
    Long gameId,
    int boardRow,
    int boardColumn,
    PieceType pieceType,
    Side pieceSide) {
}
