package domain.position;

public class SlidingDirectionFinder {

    public Direction find(Position departure, Position destination) {
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
