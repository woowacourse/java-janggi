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
        int distance = Math.abs(dx) + Math.abs(dy);

        if (dy == -forwardDirection) {
            throw new IllegalArgumentException("[ERROR] 졸/병은 후퇴가 불가능합니다.");
        }

        if (distance != 1) {
            throw new IllegalArgumentException("[ERROR] 졸/병은 한 칸만 움직일 수 있습니다.");
        }
    }
}
