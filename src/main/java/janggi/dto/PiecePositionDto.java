package janggi.dto;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.formatter.PieceFormatter;

public record PiecePositionDto(
        int row,
        int col,
        String type,
        CampDto camp
) {
    public static PiecePositionDto of(Position position, Piece piece) {
        return new PiecePositionDto(
                position.row(),
                position.column(),
                PieceFormatter.format(piece),
                CampDto.from(piece.camp())
        );
    }
}
