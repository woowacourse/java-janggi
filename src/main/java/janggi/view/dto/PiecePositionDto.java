package janggi.view.dto;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.view.formatter.PieceFormatter;

public record PiecePositionDto(
        int row,
        int col,
        String type,
        CampDto camp
) {
    public static PiecePositionDto from(Position position, Piece piece) {
        return new PiecePositionDto(
                position.row(),
                position.column(),
                PieceFormatter.format(piece),
                CampDto.from(piece.camp())
        );
    }
}
