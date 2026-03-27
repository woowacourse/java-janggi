package janggi.domain.piece;

import janggi.domain.status.Team;

public abstract class BasePiece implements Piece {

    protected final Team team;

    public BasePiece(Team team) {
        this.team = team;
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team == team;
    }
}
