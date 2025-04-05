package janggi.domain.piece.direction;

import janggi.domain.piece.position.Path;
import janggi.domain.piece.position.Position;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Movement {

    private final List<Direction> directions;

    public Movement(final List<Direction> directions) {
        this.directions = new ArrayList<>(directions);
    }

    public Movement(final Direction... givenDirections) {
        this(Arrays.asList(givenDirections));
    }

    public Path makePath(final Position startPosition, final Position arrivalPosition) {
        final List<Position> path = new ArrayList<>();
        Position currentPosition = new Position(startPosition);

        for (final Direction direction : directions) {
            currentPosition = currentPosition.move(direction);
            path.add(currentPosition);
        }

        while (!currentPosition.equals(arrivalPosition)) {
            currentPosition = currentPosition.move(directions.getFirst());
            path.add(currentPosition);
        }
        return new Path(path);
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

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Movement movement)) {
            return false;
        }
        return Objects.equals(directions, movement.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(directions);
    }
}
