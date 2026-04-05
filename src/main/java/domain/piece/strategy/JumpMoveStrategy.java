package domain.piece.strategy;

import domain.board.Position;
import domain.path.Direction;
import domain.path.PathGenerator;

import java.util.List;

public abstract class JumpMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> getPath(Position departure, Position destination) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        validateMove(deltaX, deltaY);

        Direction firstDirection = decideFirstDirection(deltaX, deltaY);
        Position intermediatePosition = intermediatePosition(departure, firstDirection);
        Direction secondDirection = decideSecondDirection(intermediatePosition, destination);

        return PathGenerator.generateComplexPath(departure, generateDirections(firstDirection, secondDirection));
    }

    protected abstract void validateMove(int deltaX, int deltaY);

    protected abstract List<Direction> generateDirections(Direction firstDirection, Direction secondDirection);

    protected Direction decideFirstDirection(int deltaX, int deltaY) {
        if ((Math.abs(deltaX) > Math.abs(deltaY))) {
            return Direction.decideDirection(deltaX, 0);
        }
        return Direction.decideDirection(0, deltaY);
    }

    protected Direction decideSecondDirection(Position intermediatePosition, Position destination) {
        return Direction.decideDirection(
                intermediatePosition.calculateDeltaX(destination),
                intermediatePosition.calculateDeltaY(destination));
    }

    protected Position intermediatePosition(Position departure, Direction firstDirection) {
        return departure.move(firstDirection.getDeltaX(), firstDirection.getDeltaY());
    }
}
