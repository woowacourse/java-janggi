package domain.path;

import domain.board.Position;

public class SingleStepLinearPathGenerator {
    public Direction decideSingleLinearDirection(Position departure, Position destination, boolean isPalacePath) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        validateSingleLinearMove(deltaX, deltaY, isPalacePath);

        return Direction.decideDirection(deltaX, deltaY);
    }

    private void validateSingleLinearMove(int deltaX, int deltaY, boolean isPalacePath) {
        if (isPalacePath && isNotSingleStep(deltaX, deltaY)) {
            return;
        }

        if (isNotLinear(deltaX, deltaY) || isNotSingleStep(deltaX, deltaY)) {
            throw new IllegalArgumentException("직선 방향으로 한 칸만 이동할 수 있습니다.");
        }
    }

    private boolean isNotLinear(int deltaX, int deltaY) {
        return deltaX != 0 && deltaY != 0;
    }

    private boolean isNotSingleStep(int deltaX, int deltaY) {
        return Math.abs(deltaX) + Math.abs(deltaY) != 1;
    }
}
