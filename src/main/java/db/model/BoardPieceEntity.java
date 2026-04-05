package db.model;

import pieces.PieceType;
import pieces.Side;

public record BoardPieceEntity(
    int row,
    int column,
    PieceType pieceType,
    Side pieceSide) {
}
