package domain.movements;

import domain.board.BoardPoint;
import domain.board.Point;
import java.util.List;

public final class DefaultMovement implements PieceMovement {
    private final List<Route> routes;

    public DefaultMovement(final List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public List<BoardPoint> calculateTotalArrivalPoints(final BoardPoint startBoardPoint) {
        return routes.stream()
                .map(route -> route.navigateArrivalPoint(startBoardPoint))
                .filter(Point::isInRange)
                .map(Point::toBoardPoint)
                .toList();
    }

    @Override
    public List<BoardPoint> calculateRoutePoints(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        for (final Route route : routes) {
            if (route.canArrive(startBoardPoint, arrivalBoardPoint)) {
                return route.getAllPointsOnRoute(startBoardPoint);
            }
        }
        throw new IllegalArgumentException("해당 도착점으로 도착할 수 없는 기물입니다.");
    }
}
