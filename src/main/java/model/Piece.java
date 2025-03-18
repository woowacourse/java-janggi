package model;

import java.util.List;

public abstract class Piece extends AbstractMove{

    protected Position position;
    private final Team team;

    public Piece(Position position, Team team) {
        this.position = position;
        this.team = team;
    }

    public Position getPosition() {
        return position;
    }

    public abstract List<Position> calculateMovablePositions();

}
