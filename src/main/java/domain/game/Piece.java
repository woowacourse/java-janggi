package domain.game;

import domain.vo.PieceType;
import domain.vo.Team;

public class Piece {
    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public String display() {
        return team.displayPiece(pieceType);
    }

    public String colorCode(String red, String green) {
        return team.colorCode(red, green);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isCannon() {
        return pieceType == PieceType.CANNON;
    }

    public boolean isOwnedBy(Team targetTeam) {
        return this.team == targetTeam;
    }

    public boolean isSameTeamAs(Piece other) {
        return this.team == other.team;
    }
}
