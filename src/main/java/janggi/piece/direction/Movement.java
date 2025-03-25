package janggi.piece.direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Movement {

    private final List<Direction> directions;

    public Movement(final List<Direction> directions) {
        this.directions = new ArrayList<>(directions);
    }

    public Movement(final Direction... givenDirections) {
        this(Arrays.asList(givenDirections));
    }

    public boolean isSameMovement(final int dy, final int dx) {
        final int totalY = sumTotalY();
        final int totalX = sumTotalX();
        return dy == totalY && dx == totalX;
    }

    private int sumTotalX() {
        return directions.stream()
                .mapToInt(Direction::getX)
                .sum();
    }

    private int sumTotalY() {
        return directions.stream()
                .mapToInt(Direction::getY)
                .sum();
    }

    public Direction getFirstDirection() {
        return directions.getFirst();
    }

    public List<Direction> getDirections() {
        return Collections.unmodifiableList(directions);
    }
}
