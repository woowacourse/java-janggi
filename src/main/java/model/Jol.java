package model;

import java.util.List;

public class Jol extends Piece{

    public Jol(Position position) {
        super(position, Team.GREEN);
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public List<Position> calculateMovablePositions() {
        return null;
    }
}

