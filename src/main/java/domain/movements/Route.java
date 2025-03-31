package domain.movements;

import domain.board.Point;
import java.util.ArrayList;
import java.util.List;

public final class Route {
    private final List<Direction> directions;

    public Route(final List<Direction> directions) {
        this.directions = directions;
    }

    public Point navigateArrivalPoint(final Point point) {
        Point result = point;
        for (final Direction direction : directions) {
            result = result.move(direction);
        }
        return result;
    }

    public boolean canArrive(final Point start, final Point arrival) {
        final Point point = navigateArrivalPoint(start);
        return point.equals(arrival);
    }

    public List<Point> retrieveAllPointsOnRoute(final Point point) {
        final List<Point> result = new ArrayList<>();
        Point currentPoint = point;
        for (final Direction direction : directions) {
            currentPoint = currentPoint.move(direction);
            result.add(currentPoint);
        }
        return result;
    }
}
