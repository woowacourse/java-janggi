package model;

import java.util.List;

public class Jol extends Piece{

    private static final int CAN_MOVE_AMOUNT = 1;

    public Jol(Position position) {
        super(position, Team.GREEN);
    }

    @Override
    public void up(int moveAmount) {
        int reverseMoveAmount = -moveAmount;
        validateMoveAmount(reverseMoveAmount);
        this.position = position.changeColumn(reverseMoveAmount);
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

    @Override
    public List<Position> calculateMovablePositions() {
        return null;
    }

    private void validateMoveAmount(int amount) {
        if (Math.abs(amount) != CAN_MOVE_AMOUNT) {
            throw new IllegalArgumentException("해당 위치로는 이동할 수 없습니다.");
        }
    }

}

