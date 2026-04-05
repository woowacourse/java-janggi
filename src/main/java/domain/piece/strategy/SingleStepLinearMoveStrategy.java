package domain.piece.strategy;

import domain.board.Position;
import domain.path.Direction;
import domain.path.PathGenerator;

import java.util.List;

public abstract class SingleStepLinearMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> getPath(Position departure, Position destination) {
        Direction direction = decideSingleLinearDirection(departure, destination);

        validateDirection(direction);

        return PathGenerator.generateStraightPath(departure, destination, direction);
    }

    protected void validateDirection(Direction direction) {
    }

    protected Direction decideSingleLinearDirection(Position departure, Position destination) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        validateSingleLinearMove(deltaX, deltaY);

        return Direction.decideDirection(deltaX, deltaY);
    }

    private void validateSingleLinearMove(int deltaX, int deltaY) {
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
