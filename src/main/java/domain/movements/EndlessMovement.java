package domain.movements;

import domain.board.BoardPoint;
import java.util.ArrayList;
import java.util.List;

public final class EndlessMovement implements PieceMovement {

    private final List<Route> routes;

    public EndlessMovement(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public List<BoardPoint> calculateTotalArrivalPoints(final BoardPoint startBoardPoint) {
        final List<BoardPoint> arrivalBoardPoints = new ArrayList<>();
        for (final Route route : routes) {
            final List<BoardPoint> pointsOnRoute = route.getAllPointsOnRoute(startBoardPoint);
            arrivalBoardPoints.addAll(pointsOnRoute);
        }
        return arrivalBoardPoints;
    }

    @Override
    public List<BoardPoint> calculateRoutePoints(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        for (final Route route : routes) {
            final List<BoardPoint> pointsOnRoute = route.getAllPointsOnRoute(startBoardPoint);
            if (pointsOnRoute.contains(arrivalBoardPoint)) {
                return pointsOnRoute.subList(0, pointsOnRoute.indexOf(arrivalBoardPoint) + 1);
            }
        }
        throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
    }
}
