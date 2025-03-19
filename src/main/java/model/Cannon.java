package model;

import java.util.List;

public class Cannon extends Piece{

    private static final int CANNOT_MOVE_AMOUNT = 1;
    
    public Cannon(Position position, Team team) {
        super(position, team);
    }

    @Override
    public void up(int moveAmount) {
        int reverseMoveAmount = -moveAmount;
        validateMoveAmount(reverseMoveAmount);
        this.position = position.changeColumn(reverseMoveAmount);
    }

    @Override
    public void down(int moveAmount) {
        validateMoveAmount(moveAmount);
        this.position = position.changeColumn(moveAmount);
    }

    @Override
    public void left(int moveAmount) {
        int reverseMoveAmount = -moveAmount;
        validateMoveAmount(reverseMoveAmount);
        this.position = position.changeRow(reverseMoveAmount);
    }

    @Override
    public void right(int moveAmount) {
        validateMoveAmount(moveAmount);
        this.position = position.changeRow(moveAmount);
    }

    private void validateMoveAmount(int amount) {
        if (Math.abs(amount) == CANNOT_MOVE_AMOUNT) {
            throw new IllegalArgumentException("해당 위치로는 이동할 수 없습니다.");
        }
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
