package dto;

import model.board.Country;
import model.pieces.PieceType;

public record PieceDto(int row, int column, Country country, PieceType pieceType) {
}
