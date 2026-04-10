package data;

import domain.piece.Camp;
import domain.piece.PieceType;

public record PieceDto(
        int column,
        int row,
        PieceType type,
        Camp camp
) {
}
