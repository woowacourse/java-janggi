package domain.piece.strategy;

import domain.board.BoardChecker;
import domain.board.Position;

public class GeneralAndGuardStrategy implements MoveStrategy {
    @Override
    public void validateMove(Position from, Position to, BoardChecker checker) {
        if (!checker.isInsidePalace(from) || !checker.isInsidePalace(to)) {
            throw new IllegalArgumentException("[ERROR] 장군/사는 궁성 밖으로 이동할 수 없습니다.");
        }

        if (isOneStepOrthogonalMove(from, to)) {
            return;
        }

        if (isDiagonalMove(from, to) && isInsidePalaceDiagonal(from, to, checker)) {
            return;
        }

        throw new IllegalArgumentException("[ERROR] 장군/사는 한 칸 직선 또는 궁 내부 대각선 이동만 가능합니다.");
    }

    private boolean isDiagonalMove(Position from, Position to) {
        int dx = Math.abs(from.calculateDx(to));
        int dy = Math.abs(from.calculateDy(to));

        return dx == 1 && dy == 1;
    }

    private boolean isOneStepOrthogonalMove(Position from, Position to) {
        int dx = Math.abs(from.calculateDx(to));
        int dy = Math.abs(from.calculateDy(to));

        return dx + dy == 1;
    }

    private boolean isInsidePalaceDiagonal(Position from, Position to, BoardChecker checker) {
        return checker.findMovePath(from, to).isPresent();
    }
}
