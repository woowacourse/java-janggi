package model;

import java.util.List;

public abstract class Piece{

    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public abstract List<List<Position>> calculateAllDirection(Position position);

    public Team getTeam() {
        return team;
    }

    public abstract boolean isCannon();
}
