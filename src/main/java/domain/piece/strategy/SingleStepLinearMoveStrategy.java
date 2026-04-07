package domain.piece.strategy;

import domain.board.Palace;
import domain.board.Position;
import domain.path.Direction;

import java.util.List;

public abstract class SingleStepLinearMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> getPath(Position departure, Position destination) {
        boolean isInPalace = isInPalace(departure, destination);
        Direction direction = decideSingleLinearDirection(departure, destination, isInPalace);

        validateMove(direction, isInPalace);

        return List.of(destination);
    }

    private boolean isInPalace(Position departure, Position destination) {
        return Palace.isInPalace(departure) && Palace.isInPalace(destination);
    }

    protected void validateMove(Direction direction, boolean isInPalace) {
    }

    protected Direction decideSingleLinearDirection(Position departure, Position destination, boolean isInPalace) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        validateSingleLinearMove(deltaX, deltaY, isInPalace);

        return Direction.decideDirection(deltaX, deltaY);
    }

    private void validateSingleLinearMove(int deltaX, int deltaY, boolean isInPalace) {
        if (isInPalace && isNotSingleStep(deltaX, deltaY)) {
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
