package janggi.dto;

import janggi.domain.Piece;
import janggi.domain.Team;

public record PieceDto(String name, Team team) {

    public static PieceDto from(Piece piece) {
        return new PieceDto(piece.getName(), piece.getTeam());
    }
}
