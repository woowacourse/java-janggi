package model;

import java.util.List;

public class Byeong extends Piece{

    private static final int CAN_MOVE_AMOUNT = 1;

    public Byeong(Position position) {
        super(position, Team.RED);
    }

    @Override
    public void left(int moveAmount) {
        validateMoveAmount(moveAmount);
        this.position = position.decreaseRow(CAN_MOVE_AMOUNT);
    }

    @Override
    public void down(int moveAmount) {
        validateMoveAmount(moveAmount);
        this.position = position.increaseColumn(CAN_MOVE_AMOUNT);
    }

    @Override
    public void right(int moveAmount) {
        validateMoveAmount(moveAmount);
        this.position = position.increaseRow(CAN_MOVE_AMOUNT);
    }

    @Override
    public List<Position> calculateMovablePositions() {
        return null;
    }

    private void validateMoveAmount(int amount) {
        if (amount != CAN_MOVE_AMOUNT) {
            throw new IllegalArgumentException("해당 위치로는 이동할 수 없습니다.");
        }
    }
}
