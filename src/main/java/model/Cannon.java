package model;

import java.util.List;

public class Cannon extends Piece{
    public Cannon(Position position, Team team) {
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
