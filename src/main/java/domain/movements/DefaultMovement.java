package domain.movements;

import domain.board.Point;
import exceptions.JanggiGameRuleWarningException;
import java.util.List;
import java.util.Objects;

public final class DefaultMovement implements PieceMovement {
    private final List<Route> routes;

    public DefaultMovement(final List<Route> routes) {
        this.routes = Objects.requireNonNull(routes, "경로 정보가 NULL일 수 없습니다.");
    }

    @Override
    public List<Point> searchTotalArrivalPoints(final Point start) {
        return routes.stream()
                .map(route -> route.navigateArrivalPoint(start))
                .toList();
    }

    @Override
    public List<Point> calculatePointsOnRoute(final Point start, final Point arrival) {
        return routes.stream()
                .filter(route -> route.canArrive(start, arrival))
                .findFirst()
                .orElseThrow(() -> new JanggiGameRuleWarningException("해당 도착점으로 도착할 수 없는 기물입니다."))
                .retrieveAllPointsOnRoute(start);
    }
}
