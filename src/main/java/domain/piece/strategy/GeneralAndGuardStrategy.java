package domain.piece.strategy;

import domain.board.PathChecker;
import domain.board.Position;

public class GeneralAndGuardStrategy implements MoveStrategy {

    private static final String CAN_MOVE_ONLY_ONE_POSITION_ERROR_MESSAGE = "[ERROR] 한 칸만 움직일 수 있습니다.";
    private static final String CAN_MOVE_ONLY_IN_PALACE_ERROR_MESSAGE = "[ERROR] 궁과 사는 궁성 내부에서만 움직일 수 있습니다.";
    private static final String DIAGONAL_MOVE_INCLUDE_PALACE_CENTER_ERROR_MESSAGE = "[ERROR] 대각선 이동은 궁성 중앙을 포함해야 합니다.";

    @Override
    public void move(Position from, Position to, PathChecker checker) {
        checkInPalace(from, to, checker);

        int dx = Math.abs(from.calculateDx(to));
        int dy = Math.abs(from.calculateDy(to));

        if (dx == 1 && dy == 1) {
            checkOnPalaceCenter(from, to, checker);
            return;
        }

        if (dx + dy != 1) {
            throw new IllegalArgumentException(CAN_MOVE_ONLY_ONE_POSITION_ERROR_MESSAGE);
        }
    }

    private void checkInPalace(Position from, Position to, PathChecker checker) {
        if (!(checker.isInPalace(from) && checker.isInPalace(to))) {
            throw new IllegalArgumentException(CAN_MOVE_ONLY_IN_PALACE_ERROR_MESSAGE);
        }
    }

    private void checkOnPalaceCenter(Position from, Position to, PathChecker checker) {
        if (!checker.isOnPalaceCenter(from) && !checker.isOnPalaceCenter(to)) {
            throw new IllegalArgumentException(DIAGONAL_MOVE_INCLUDE_PALACE_CENTER_ERROR_MESSAGE);
        }
    }
}
