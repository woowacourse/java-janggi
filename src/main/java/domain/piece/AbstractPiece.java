package domain.piece;

import domain.Team;

public abstract class AbstractPiece implements Piece {

    protected final Team team;

    protected AbstractPiece(final Team team) {
        this.team = team;
    }

    public final boolean isGreenTeam() {
        return team.isGreenTeam();
    }
}
