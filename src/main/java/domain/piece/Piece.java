package domain.piece;

import domain.Team;

public abstract class Piece {
    protected final Team team;

    protected Piece(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}
