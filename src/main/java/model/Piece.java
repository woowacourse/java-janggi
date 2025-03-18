package model;

import java.util.List;

public abstract class Piece extends AbstractMove{

    protected Position position;

    public Piece(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public abstract List<Position> calculateMovablePositions();

}
