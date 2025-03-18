package model;

import java.util.List;

public class Byeong extends Piece{

    public Byeong(Position position) {
        super(position, Team.RED);
    }

    @Override
    public void left() {
        this.position = position.decreaseRow(1);
    }

    @Override
    public void down() {
        this.position = position.increaseColumn(1);
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
