package domain.movements;

import domain.board.Point;
import execptions.JanggiGameRuleWarningException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class EndlessMovement implements PieceMovement {

    private static final int MAX_DIRECTION_COUNT = 10;
    private final List<Route> routes;

    public EndlessMovement() {
        this.routes = getDefaultRoutes();
    }

    @Override
    public List<Point> calculateTotalArrivalPoints(final Point startPoint) {
        return routes.stream()
                .map(route -> route.getAllPointsOnRoute(startPoint))
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public List<Point> calculateRoutePoints(final Point startPoint, final Point arrivalPoint) {
        return routes.stream()
                .map(route -> route.getAllPointsOnRoute(startPoint))
                .filter(points -> points.contains(arrivalPoint))
                .map(points -> points.subList(0, points.indexOf(arrivalPoint) + 1))
                .findFirst()
                .orElseThrow(() -> new JanggiGameRuleWarningException("해당 위치로 이동할 수 없습니다."));
    }

    private List<Route> getDefaultRoutes() {
        return List.of(
                new Route(Collections.nCopies(MAX_DIRECTION_COUNT, Direction.NORTH)),
                new Route(Collections.nCopies(MAX_DIRECTION_COUNT, Direction.EAST)),
                new Route(Collections.nCopies(MAX_DIRECTION_COUNT, Direction.SOUTH)),
                new Route(Collections.nCopies(MAX_DIRECTION_COUNT, Direction.WEST))
        );
    }
}
