package domain.piece.strategy;

import domain.board.PathChecker;
import domain.board.Position;
import domain.piece.Camp;

public class SoldierStrategy implements MoveStrategy {
    private static final String INVALID_SOLDIER_MOVE_ERROR_MESSAGE = "[ERROR] 졸/병은 후퇴가 불가능합니다.";
    private static final String CAN_MOVE_ONLY_ONE_SPACE_ERROR_MESSAGE = "[ERROR] 졸/병은 한 칸만 움직일 수 있습니다.";
    private static final String CAN_MOVE_DIAGONAL_INCLUDE_CENTER_ERROR_MESSAGE = "[ERROR] 대각선 이동은 중앙을 포함해야 합니다.";

    @Override
    public void move(Position from, Position to, PathChecker checker) {
        Camp camp = checker.findCamp(from);
        int forwardDirection = camp.forward();

        int dy = from.calculateDy(to);
        int distance = calculateDistance(from, to);

        validateForwardDirection(from, to, forwardDirection);

        if (checker.isInPalace(from) && dy == forwardDirection) {
            validateForwardMove(from, to, checker);
            return;
        }

        validateMoveOneSpace(distance);
    }

    private int calculateDistance(Position from, Position to) {
        return Math.abs(from.calculateDx(to)) + Math.abs(from.calculateDy(to));
    }

    private void validateForwardDirection(Position from, Position to, int forwardDirection) {
        int dy = from.calculateDy(to);

        if (dy == -forwardDirection) {
            throw new IllegalArgumentException(INVALID_SOLDIER_MOVE_ERROR_MESSAGE);
        }
    }

    private void validateForwardMove(Position from, Position to, PathChecker pathChecker) {
        int distance = calculateDistance(from, to);

        if (distance == 1) {
            return;
        }

        if (distance == 2) {
            validateForwardDiagonalMove(from, to, pathChecker);
            return;
        }

        validateMoveOneSpace(distance);
    }

    private void validateForwardDiagonalMove(Position from, Position to, PathChecker pathChecker) {
        if (!pathChecker.isOnPalaceCenter(from) && !pathChecker.isOnPalaceCenter(to)) {
            throw new IllegalArgumentException(CAN_MOVE_DIAGONAL_INCLUDE_CENTER_ERROR_MESSAGE);
        }
    }

    private void validateMoveOneSpace(int distance) {
        if (distance != 1) {
            throw new IllegalArgumentException(CAN_MOVE_ONLY_ONE_SPACE_ERROR_MESSAGE);
        }
    }

}
