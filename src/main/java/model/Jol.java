package model;

import java.util.List;

public class Jol extends Piece{

    public Jol(Position position) {
        super(position, Team.GREEN);
    }

    @Override
    public void up() {
        this.position = position.decreaseColumn(1);
    }

    @Override
    public void left() {
        this.position = position.decreaseRow(1);
    }

    @Override
    public void right() {
        this.position = position.increaseRow(1);
    }

    @Override
    public List<Position> calculateMovablePositions() {
        return null;
    }
}

