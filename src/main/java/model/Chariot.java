package model;

import java.util.List;

public class Chariot extends Piece{

    public Chariot(Position position, Team team) {
        super(position, team);
    }

    @Override
    public void up(int moveAmount) {
        int reverseMoveAmount = -moveAmount;
        this.position = position.changeColumn(reverseMoveAmount);
    }

    @Override
    public void down(int moveAmount) {
        this.position = position.changeColumn(moveAmount);
    }

    @Override
    public void left(int moveAmount) {
        int reverseMoveAmount = -moveAmount;
        this.position = position.changeRow(reverseMoveAmount);
    }

    @Override
    public void right(int moveAmount) {
        this.position = position.changeRow(moveAmount);
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
