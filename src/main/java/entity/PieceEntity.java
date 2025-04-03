package entity;

import domain.janggiPiece.Piece;
import domain.type.JanggiTeam;

public record PieceEntity(
        int row,
        int col,
        JanggiTeam team,
        Piece type
) {
    public String getTeamName() {
        return team.name;
    }

    public String getPieceName() {
        return type.name;
    }
}
