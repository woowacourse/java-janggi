package repository;

import model.board.Country;
import model.pieces.PieceType;

public record SavedPiece(
        int row,
        int col,
        Country country,
        PieceType pieceType
) {
}
