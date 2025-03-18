package model;

import java.util.List;

public class Horse extends  Piece{

    public Horse(Position position, Team team) {
        super(position, team);
    }

    @Override
    public void diagonalUpLeft() {
        this.position = position.changeColumnAndRow(-2, -1);
    }

    @Override
    public void diagonalUpRight() {
        this.position = position.changeColumnAndRow(-2, 1);
    }

    @Override
    public void diagonalLeftUp() {
        this.position = position.changeColumnAndRow(-1, -2);
    }

    @Override
    public void diagonalLeftDown() {
        this.position = position.changeColumnAndRow(1, -2);
    }

    @Override
    public void diagonalRightUp() {
        this.position = position.changeColumnAndRow(-1, 2);
    }

    @Override
    public void diagonalRightDown() {
        this.position = position.changeColumnAndRow(1, 2);
    }

    @Override
    public void diagonalDownLeft() {
        this.position = position.changeColumnAndRow(2, -1);
    }

    @Override
    public void diagonalDownRight() {
        this.position = position.changeColumnAndRow(2, 1);
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
