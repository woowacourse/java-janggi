package entity;

import domain.janggiPiece.JanggiChessPiece;
import domain.janggiPiece.Piece;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;

public record PieceEntity(
        int row,
        int col,
        JanggiTeam team,
        Piece type
) {
    public PieceEntity move(JanggiPosition to) {
        return new PieceEntity(to.getRow(), to.getCol(), team, type);
    }

    public JanggiChessPiece createPiece() {
        return type().create(team);
    }

    public String getTeamName() {
        return team.name;
    }

    public String getPieceName() {
        return type.name;
    }
}
