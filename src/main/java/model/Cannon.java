package model;

import java.util.List;

public class Cannon extends Piece{
    public Cannon(Team team) {
        super(team);
    }

    @Override
    protected void initializePosition(Team team) {

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
