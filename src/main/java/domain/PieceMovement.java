package domain;

import domain.piece.Route;
import java.util.List;

public class PieceMovement {
    private final List<Route> routes;

    public PieceMovement(List<Route> routes) {
        this.routes = routes;
    }

    public List<Point> calculateTotalArrivalPoints(Point startPoint) {
        return routes.stream()
                .map(route -> route.navigateArrivalPoint(startPoint))
                .toList();
    }

    public List<Point> calculateRoutePoints(Point startPoint, Point arrivalPoint) {
        for (Route route : routes) {
            if (route.canArrive(startPoint, arrivalPoint)) {
                return route.getAllPointsOnRoute(startPoint);
            }
        }
        throw new IllegalArgumentException("해당 도착지점으로 도착할 수 없는 기물입니다.");
    }
}
