package domain.movements;

import domain.board.Point;
import exceptions.JanggiGameRuleWarningException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class StraightLineMovement implements PieceMovement {
    private static final int MAX_DIRECTION_COUNT = 10;

    public StraightLineMovement(List<Route> routes) {
        this.routes = Objects.requireNonNull(routes, "경로 정보가 NULL일 수 없습니다.");
    }

    private final List<Route> routes;

    public StraightLineMovement() {
        this.routes = getDefaultRoutes();
    }

    public static StraightLineMovement generateInRangeOfPalace() {
        List<Route> routes = new ArrayList<>(getDefaultRoutes());
        routes.add(new Route(List.of(Direction.NORTHEAST, Direction.NORTHEAST)));
        routes.add(new Route(List.of(Direction.NORTHWEST, Direction.NORTHWEST)));
        routes.add(new Route(List.of(Direction.SOUTHEAST, Direction.SOUTHEAST)));
        routes.add(new Route(List.of(Direction.SOUTHWEST, Direction.SOUTHWEST)));
        return new StraightLineMovement(routes);
    }

    private static List<Route> getDefaultRoutes() {
        return List.of(
                new Route(Collections.nCopies(MAX_DIRECTION_COUNT, Direction.NORTH)),
                new Route(Collections.nCopies(MAX_DIRECTION_COUNT, Direction.EAST)),
                new Route(Collections.nCopies(MAX_DIRECTION_COUNT, Direction.SOUTH)),
                new Route(Collections.nCopies(MAX_DIRECTION_COUNT, Direction.WEST))
        );
    }

    @Override
    public List<Point> searchTotalArrivalPoints(final Point start) {
        return routes.stream()
                .map(route -> route.retrieveAllPointsOnRoute(start))
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public List<Point> calculatePointsOnRoute(final Point start, final Point arrival) {
        return routes.stream()
                .map(route -> route.retrieveAllPointsOnRoute(start))
                .filter(points -> points.contains(arrival))
                .map(points -> points.subList(0, points.indexOf(arrival) + 1))
                .findFirst()
                .orElseThrow(() -> new JanggiGameRuleWarningException("해당 위치로 이동할 수 없습니다."));
    }
}
