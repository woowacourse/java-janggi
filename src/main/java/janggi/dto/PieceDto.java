package janggi.dto;

import janggi.domain.piece.Piece;

public record PieceDto(String type, String side) {
    public static PieceDto from(Piece piece) {
        return new PieceDto(
                piece.getType().name(),
                piece.getSide().name()
        );
    }
}
