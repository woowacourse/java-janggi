package janggi.dto;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

public record PieceDto(String type, Side side) {

    public static PieceDto from(Piece piece) {
        PieceType pieceType = piece.getPieceType();
        Side side = piece.getSide();
        return new PieceDto(pieceType.getNameFormat(), side);
    }
}
