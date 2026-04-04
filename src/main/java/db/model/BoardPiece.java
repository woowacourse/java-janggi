package db.model;

import pieces.PieceType;
import pieces.Side;

public record BoardPiece(
    int row,
    int column,
    PieceType pieceType,
    Side pieceSide) {
}
