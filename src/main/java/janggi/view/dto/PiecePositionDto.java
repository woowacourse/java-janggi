package janggi.view.dto;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.view.format.PieceFormat;

public record PiecePositionDto(
        int row,
        int col,
        String type,
        CampDto camp
) {
    public static PiecePositionDto from(Position position, Piece piece) {
        PieceFormat pieceFormat = PieceFormat.from(piece);
        return new PiecePositionDto(
                position.row(),
                position.column(),
                pieceFormat.symbol(),
                CampDto.from(piece.getCamp())
        );
    }
}
