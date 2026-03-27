package domain.piece.strategy;

import domain.board.PathChecker;
import domain.board.Position;
import domain.piece.Camp;

public class SoldierStrategy implements MoveStrategy {
    private static final String INVALID_SOLDIER_MOVE_ERROR_MESSAGE = "[ERROR] 졸/병은 앞으로 한 칸, 또는 좌우로 한 칸만 이동할 수 있습니다. (후퇴 불가)";

    @Override
    public void move(Position from, Position to, PathChecker checker) {
        Camp camp = checker.findCamp(from);
        int forwardDirection = camp.forward();

        int dx = from.calculateDx(to);
        int dy = from.calculateDy(to);
        int distance = Math.abs(dx) + Math.abs(dy);

        if (dy == -forwardDirection || distance != 1) {
            throw new IllegalArgumentException(INVALID_SOLDIER_MOVE_ERROR_MESSAGE);
        }
    }
}
