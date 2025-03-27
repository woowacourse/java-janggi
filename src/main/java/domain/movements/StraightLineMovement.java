package domain.movements;

import domain.board.Point;
import exceptions.JanggiGameRuleWarningException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class StraightLineMovement implements PieceMovement {

    private static final int MAX_DIRECTION_COUNT = 10;
    private final List<Route> routes;

    public StraightLineMovement() {
        this.routes = getDefaultRoutes();
    }

    @Override
    public List<Point> calculateTotalArrivalPoints(final Point start) {
        return routes.stream()
                .map(route -> route.getAllPointsOnRoute(start))
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public List<Point> calculatePointsOnRoute(final Point start, final Point arrival) {
        return routes.stream()
                .map(route -> route.getAllPointsOnRoute(start))
                .filter(points -> points.contains(arrival))
                .map(points -> points.subList(0, points.indexOf(arrival) + 1))
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
