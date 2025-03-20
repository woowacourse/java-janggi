package model;

import java.util.List;

public abstract class Piece{

    protected Position position;
    private final Team team;

    public Piece(Position position, Team team) {
        this.position = position;
        this.team = team;
    }

    public abstract List<List<Position>> calculateAllDirection();

    public Position getPosition() {
        return position;
    }

    public Team getTeam() {
        return team;
    }

    public void changePosition(Position position) {
        this.position = position;
    }

    public abstract boolean isCannon();
}
