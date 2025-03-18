package domain.piece;

import domain.Team;

public abstract class Piece {

    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    protected abstract boolean canMove(int startRow, int startColumn, int targetRow, int targetColumn);

    public Team getTeam() {
        return team;
    }
}
