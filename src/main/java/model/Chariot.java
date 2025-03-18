package model;

import java.util.List;

public class Chariot extends Piece{

    public Chariot(Position position, Team team) {
        super(position, team);
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
