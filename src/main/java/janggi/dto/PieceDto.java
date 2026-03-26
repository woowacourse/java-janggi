package janggi.dto;

import janggi.domain.piece.Piece;

public record PieceDto(String pieceName, String teamName) {
    public static PieceDto from(Piece piece) {
        return new PieceDto(piece.getPieceName(), piece.getTeamName());
    }
}
