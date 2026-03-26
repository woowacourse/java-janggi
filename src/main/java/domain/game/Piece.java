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
        return team.getPrefix() + pieceType.getDisplayName();
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isCannon() {
        return pieceType == PieceType.CANNON;
    }
}
