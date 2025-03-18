package model;

import java.util.List;

public abstract class Piece extends AbstractMove{

    protected Position position;
    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    protected abstract void initializePosition(Team team);

    public Position getPosition() {
        return position;
    }

    public abstract List<Position> calculateMovablePositions();

}
