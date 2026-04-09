package janggi.dto;

import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;

public record BoardSpot(String pieceName, TeamType teamType) {

    public static BoardSpot from(Piece piece) {
        return new BoardSpot(piece.name(), piece.getTeamType());
    }
}
