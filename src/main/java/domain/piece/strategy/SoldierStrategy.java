package domain.piece.strategy;

import domain.board.BoardChecker;
import domain.board.Position;
import domain.piece.Camp;

public class SoldierStrategy implements MoveStrategy {
    private final Camp camp;

    public SoldierStrategy(Camp camp) {
        this.camp = camp;
    }

    @Override
    public void move(Position from, Position to, BoardChecker checker) {
        int forwardDirection = camp.forward();

        int dx = from.calculateDx(to);
        int dy = from.calculateDy(to);

        validateSoldierBackMove(dy, forwardDirection);
        validateSoldierMoveOne(dx, dy);
    }

    private static void validateSoldierMoveOne(int dx, int dy) {
        int distance = Math.abs(dx) + Math.abs(dy);
        if (distance != 1) {
            throw new IllegalArgumentException("[ERROR] 졸/병은 한 칸만그 움직일 수 있습니다.");
        }
    }

    private static void validateSoldierBackMove(int dy, int forwardDirection) {
        if (dy == -forwardDirection) {
            throw new IllegalArgumentException("[ERROR] 졸/병은 후퇴가 불가능합니다.");
        }
    }
}
