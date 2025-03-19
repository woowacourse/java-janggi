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
}
