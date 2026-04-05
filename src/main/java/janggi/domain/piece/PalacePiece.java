package janggi.domain.piece;

import janggi.domain.team.Team;

public abstract class PalacePiece extends MoveablePiece {

    protected final Palace palace;

    public PalacePiece(Team team, Palace palace) {
        super(team);
        this.palace = palace;
    }
}
