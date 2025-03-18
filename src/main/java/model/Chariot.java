package model;

import java.util.List;

public class Chariot extends Piece{

    public Chariot(Position position, Team team) {
        super(position, team);
    }

    @Override
    public void up(int moveAmount) {
        this.position = position.decreaseColumn(moveAmount);
    }

    @Override
    public void down(int moveAmount) {
        this.position = position.increaseColumn(moveAmount);

    }

    @Override
    public void left(int moveAmount) {
        this.position = position.decreaseRow(moveAmount);
    }

    @Override
    public void right(int moveAmount) {
        this.position = position.increaseRow(moveAmount);
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
