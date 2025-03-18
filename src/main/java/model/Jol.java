package model;

import java.util.List;

public class Jol extends Piece{
    public Jol(Team team) {
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

