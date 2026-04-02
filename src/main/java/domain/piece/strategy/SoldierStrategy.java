package domain.piece.strategy;

import domain.board.PathChecker;
import domain.board.Position;
import domain.piece.Camp;

public class SoldierStrategy implements MoveStrategy {
    private static final String INVALID_SOLDIER_MOVE_ERROR_MESSAGE = "[ERROR] 졸/병은 후퇴가 불가능합니다.";

    @Override
    public void move(Position from, Position to, PathChecker checker) {
        Camp camp = checker.findCamp(from);
        int forwardDirection = camp.forward();

        int dx = from.calculateDx(to);
        int dy = from.calculateDy(to);
        int distance = Math.abs(dx) + Math.abs(dy);

        if (dy == -forwardDirection) {
            throw new IllegalArgumentException(INVALID_SOLDIER_MOVE_ERROR_MESSAGE);
        }

        if (distance != 1) {
            throw new IllegalArgumentException("[ERROR] 졸/병은 한 칸만 움직일 수 있습니다.");
        }
    }
}
