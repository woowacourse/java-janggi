package domain.movement;

import domain.position.Position;

public class SlidingDirectionFinder {

    public Direction find(Position departure, Position destination) {
        if (departure.isRightUp(destination)) {
            return Direction.RIGHT_UP;
        }
        if (departure.isLeftUp(destination)) {
            return Direction.LEFT_UP;
        }
        if (departure.isRightDown(destination)) {
            return Direction.RIGHT_DOWN;
        }
        if (departure.isLeftDown(destination)) {
            return Direction.LEFT_DOWN;
        }
        if (departure.isSameRow(destination) && destination.isLeftColumn(departure)) {
            return Direction.LEFT;
        }
        if (departure.isSameRow(destination)) {
            return Direction.RIGHT;
        }
        if (destination.isLowerRowThan(departure)) {
            return Direction.DOWN;
        }
        return Direction.UP;
    }
}
