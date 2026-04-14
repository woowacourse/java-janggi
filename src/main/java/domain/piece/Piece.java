package domain.piece;

import domain.Team;

public abstract class Piece {
    protected final Team team;
    protected final PieceType pieceType;

    protected Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isOtherTeam(Team compareTeam) {
        return this.team != compareTeam;
    }

    public boolean isCannon() {
        return false;
    }

    public abstract boolean isBlank();
}
