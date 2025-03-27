package domain.movements;

import domain.board.Point;
import domain.board.TempPoint;
import execptions.JanggiArgumentException;
import java.util.ArrayList;
import java.util.List;

public final class Route {
    private final List<Direction> directions;

    public Route(final List<Direction> directions) {
        this.directions = directions;
    }

    public TempPoint navigateArrivalPoint(Point startPoint) {
        TempPoint tempPoint = startPoint.toTempPoint();
        for (final Direction direction : directions) {
            tempPoint = tempPoint.move(direction);
        }
        return tempPoint;
    }

    public boolean canArrive(final Point startPoint, final Point arrivalPoint) {
        final TempPoint point = navigateArrivalPoint(startPoint);
        if (!point.isInRange()) {
            return false;
        }
        return point.toPoint().equals(arrivalPoint);
    }

    public List<Point> getAllPointsOnRoute(Point point) {
        final List<Point> result = new ArrayList<>();
        try {
            for (final Direction direction : directions) {
                point = point.move(direction);
                result.add(point);
            }
            return result;
        } catch (JanggiArgumentException e) {
            return result;
        }
    }
}
