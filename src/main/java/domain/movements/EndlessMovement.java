package domain.movements;

import domain.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EndlessMovement implements PieceMovement {
    private final List<Route> routes;

    public EndlessMovement() {
        this.routes = List.of(
                new Route(Collections.nCopies(10, Direction.NORTH)),
                new Route(Collections.nCopies(10, Direction.EAST)),
                new Route(Collections.nCopies(10, Direction.SOUTH)),
                new Route(Collections.nCopies(10, Direction.WEST))
        );
    }

    @Override
    public List<Point> calculateTotalArrivalPoints(final Point startPoint) {
        final List<Point> arrivalPoints = new ArrayList<>();
        for (final Route route : routes) {
            List<Point> pointsOnRoute = route.getAllPointsOnRoute(startPoint);
            arrivalPoints.addAll(pointsOnRoute);
        }
        return arrivalPoints;
    }

    @Override
    public List<Point> calculateRoutePoints(final Point startPoint, final Point arrivalPoint) {
        for (final Route route : routes) {
            final List<Point> pointsOnRoute = route.getAllPointsOnRoute(startPoint);
            if (pointsOnRoute.contains(arrivalPoint)) {
                return pointsOnRoute.subList(0, pointsOnRoute.indexOf(arrivalPoint) + 1);
            }
        }
        throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
    }
}
