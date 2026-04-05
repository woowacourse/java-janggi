package domain.piece.strategy;

import domain.path.Direction;

import java.util.List;

public class ElephantMoveStrategy extends JumpMoveStrategy {
    @Override
    protected void validateMove(int deltaX, int deltaY) {
        if (!isElephantMove(deltaX, deltaY)) {
            throw new IllegalArgumentException("마는 직진 후, 대각선 방향으로 한 칸 이동 가능합니다.");
        }
    }

    @Override
    protected List<Direction> generateDirections(Direction firstDirection, Direction secondDirection) {
        return List.of(firstDirection, secondDirection, secondDirection);
    }

    private boolean isElephantMove(int deltaX, int deltaY) {
        int absoluteX = Math.abs(deltaX);
        int absoluteY = Math.abs(deltaY);

        return (absoluteX == 2 && absoluteY == 3) || (absoluteX == 3 && absoluteY == 2);
    }
}
