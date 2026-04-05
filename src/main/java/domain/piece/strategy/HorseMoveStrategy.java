package domain.piece.strategy;

import domain.path.Direction;

import java.util.List;

public class HorseMoveStrategy extends JumpMoveStrategy {
    @Override
    protected void validateMove(int deltaX, int deltaY) {
        if (!isHorseMove(deltaX, deltaY)) {
            throw new IllegalArgumentException("상은 직진 후, 대각선 방향으로 두 칸 이동 가능합니다.");
        }
    }

    @Override
    protected List<Direction> generateDirections(Direction firstDirection, Direction secondDirection) {
        return List.of(firstDirection, secondDirection);
    }

    private boolean isHorseMove(int deltaX, int deltaY) {
        int absoluteX = Math.abs(deltaX);
        int absoluteY = Math.abs(deltaY);

        return (absoluteX == 1 && absoluteY == 2) || (absoluteX == 2 && absoluteY == 1);
    }
}
