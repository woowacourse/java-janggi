package janggi.dto;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.view.format.PieceFormat;

public record PiecePositionDto(
        int row,
        int column,
        String type,
        Camp camp
) {
    public static PiecePositionDto of(Position position, Piece piece) {
        PieceFormat pieceFormat = PieceFormat.from(piece);
        return new PiecePositionDto(
                position.row(),
                position.column(),
                pieceFormat.getFormat(),
                piece.camp()
        );
    }
}
