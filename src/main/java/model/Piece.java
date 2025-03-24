package model;

import java.util.List;

public abstract class Piece{

    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public abstract List<Position> calculateAllDirection(Position departure, Position arrival);

    public abstract boolean isCannon();

    public boolean isSameTeam(Piece piece) {
        return this.team == piece.getTeam();
    }

    public Team getTeam() {
        return team;
    }
}
