package janggi.dto;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.camp.CampType;
import janggi.view.format.PieceFormat;

public record PiecePositionDto(
        int row,
        int column,
        String type,
        CampType campType
) {
    public static PiecePositionDto of(Position position, Piece piece) {
        PieceFormat pieceFormat = PieceFormat.from(piece);
        return new PiecePositionDto(
                position.row(),
                position.column(),
                pieceFormat.getFormat(),
                piece.campType()
        );
    }
}
