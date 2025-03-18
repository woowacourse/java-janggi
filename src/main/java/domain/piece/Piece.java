package domain.piece;

import domain.Team;

public abstract class Piece {

    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}
