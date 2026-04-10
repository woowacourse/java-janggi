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
    public void validateMove(Position from, Position to, BoardChecker checker) {
        int dx = from.calculateDx(to);
        int dy = from.calculateDy(to);

        validateSoldierBackMove(dy, camp.forward());

        if (isOrthogonalMove(dx, dy)) {
            validateOrthogonalMove(dx, dy);
            return;
        }

        validatePalaceDiagonalMove(from, to, checker, dx, dy);
    }

    private boolean isOrthogonalMove(int dx, int dy) {
        return dx == 0 || dy == 0;
    }

    private static void validateSoldierBackMove(int dy, int forwardDirection) {
        if (dy == -forwardDirection) {
            throw new IllegalArgumentException("[ERROR] 졸/병은 후퇴가 불가능합니다.");
        }
    }

    private void validateOrthogonalMove(int dx, int dy) {
        if (Math.abs(dx) + Math.abs(dy) != 1) {
            throw new IllegalArgumentException("[ERROR] 졸/병은 좌우 또는 전진으로 한 칸만 이동할 수 있습니다.");
        }
    }

    private void validatePalaceDiagonalMove(Position from, Position to, BoardChecker checker, int dx, int dy) {
        if (Math.abs(dx) != 1 || Math.abs(dy) != 1) {
            throw new IllegalArgumentException("[ERROR] 졸/병은 대각선으로 한 칸만 이동할 수 있습니다.");
        }

        if (dy != camp.forward()) {
            throw new IllegalArgumentException("[ERROR] 졸/병은 전진 방향으로만 이동할 수 있습니다.");
        }

        if (checker.findMovePath(from, to).isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 졸/병은 궁성 안에서만 대각선 이동할 수 있습니다.");
        }
    }
}
