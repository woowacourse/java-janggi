package domain.movements;

import domain.Point;
import java.util.List;

public class DefaultMovement implements PieceMovement {
    private final List<Route> routes;

    public DefaultMovement(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public List<Point> calculateTotalArrivalPoints(Point startPoint) {
        return routes.stream()
                .map(route -> route.navigateArrivalPoint(startPoint))
                .toList();
    }

    @Override
    public List<Point> calculateRoutePoints(Point startPoint, Point arrivalPoint) {
        for (Route route : routes) {
            if (route.canArrive(startPoint, arrivalPoint)) {
                return route.getAllPointsOnRoute(startPoint);
            }
        }
        throw new IllegalArgumentException("해당 도착지점으로 도착할 수 없는 기물입니다.");
    }
}
