package domain.movement;

import domain.position.Position;

public class SlidingDirectionFinder {

    public Direction find(Position departure, Position destination) {
        if (isDiagonal(departure, destination)) {
            return findDiagonalDirection(departure, destination);
        }
        if (departure.isSameRow(destination)) {
            return findHorizontalDirection(departure, destination);
        }
        return findVerticalDirection(departure, destination);
    }

    private boolean isDiagonal(Position departure, Position destination) {
        return departure.isRightUp(destination)
                || departure.isLeftUp(destination)
                || departure.isRightDown(destination)
                || departure.isLeftDown(destination);
    }

    private Direction findDiagonalDirection(Position departure, Position destination) {
        if (departure.isRightUp(destination)) {
            return Direction.RIGHT_UP;
        }
        if (departure.isLeftUp(destination)) {
            return Direction.LEFT_UP;
        }
        if (departure.isRightDown(destination)) {
            return Direction.RIGHT_DOWN;
        }
        return Direction.LEFT_DOWN;
    }

    private Direction findHorizontalDirection(Position departure, Position destination) {
        if (destination.isLeftColumn(departure)) {
            return Direction.LEFT;
        }
        return Direction.RIGHT;
    }

    private Direction findVerticalDirection(Position departure, Position destination) {
        if (destination.isLowerRowThan(departure)) {
            return Direction.DOWN;
        }
        return Direction.UP;
    }
}
