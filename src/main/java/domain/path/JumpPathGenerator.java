package domain.path;

import domain.board.Position;

import java.util.ArrayList;
import java.util.List;

public class JumpPathGenerator {
    public List<Position> getPath(Position departure, Position destination, int jumpCount) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        Direction firstDirection = decideFirstDirection(deltaX, deltaY);
        Position intermediatePosition = intermediatePosition(departure, firstDirection);
        Direction secondDirection = decideSecondDirection(intermediatePosition, destination);

        return generateComplexPath(departure, generateJumpDirections(firstDirection, secondDirection, jumpCount));
    }

    private List<Direction> generateJumpDirections(Direction firstDirection, Direction secondDirection, int jumpCount) {
        List<Direction> directions = new ArrayList<>();

        directions.add(firstDirection);
        for (int i = 0; i < jumpCount; i++) {
            directions.add(secondDirection);
        }
        return directions;
    }

    private Direction decideFirstDirection(int deltaX, int deltaY) {
        if ((Math.abs(deltaX) > Math.abs(deltaY))) {
            return Direction.decideDirection(deltaX, 0);
        }
        return Direction.decideDirection(0, deltaY);
    }

    private Direction decideSecondDirection(Position intermediatePosition, Position destination) {
        return Direction.decideDirection(
                intermediatePosition.calculateDeltaX(destination),
                intermediatePosition.calculateDeltaY(destination));
    }

    private Position intermediatePosition(Position departure, Direction firstDirection) {
        return departure.move(firstDirection.getDeltaX(), firstDirection.getDeltaY());
    }

    private List<Position> generateComplexPath(Position departure, List<Direction> directions) {
        List<Position> paths = new ArrayList<>();

        Position current = departure;
        for (Direction direction : directions) {
            current = current.move(direction.getDeltaX(), direction.getDeltaY());
            paths.add(current);
        }

        return paths;
    }
}
