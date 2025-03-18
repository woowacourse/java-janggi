package model;

import java.util.List;

public class General extends Piece{

    public General(Position position, Team team) {
        super(position, team);
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
