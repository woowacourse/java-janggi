package model;

import java.util.List;

public class Elephant extends Piece{

    public Elephant(Position position, Team team) {
        super(position, team);
    }

    @Override
    public void diagonalUpLeft() {
        this.position = position.changeColumnAndRow(-3, -2);
    }

    @Override
    public void diagonalUpRight() {
        this.position = position.changeColumnAndRow(-3, 2);
    }

    @Override
    public void diagonalLeftUp() {
        this.position = position.changeColumnAndRow(-2, -3);
    }

    @Override
    public void diagonalLeftDown() {
        this.position = position.changeColumnAndRow(2, -3);
    }

    @Override
    public void diagonalRightUp() {
        this.position = position.changeColumnAndRow(-2, 3);
    }

    @Override
    public void diagonalRightDown() {
        this.position = position.changeColumnAndRow(2, 3);
    }

    @Override
    public void diagonalDownLeft() {
        this.position = position.changeColumnAndRow(3, -2);
    }

    @Override
    public void diagonalDownRight() {
        this.position = position.changeColumnAndRow(3, 2);
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
