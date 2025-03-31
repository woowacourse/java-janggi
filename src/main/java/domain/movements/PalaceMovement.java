package domain.movements;

import domain.board.Palace;
import domain.board.Point;
import exceptions.JanggiGameRuleWarningException;
import java.util.ArrayList;
import java.util.List;

public final class PalaceMovement implements PieceMovement {
    private final List<Route> routes;

    public PalaceMovement() {
        this.routes = generateDefaultRoutes();
    }

    @Override
    public List<Point> searchTotalArrivalPoints(final Point start) {
        return routes.stream()
                .map(route -> route.navigateArrivalPoint(start))
                .toList();
    }

    @Override
    public List<Point> calculatePointsOnRoute(final Point start, final Point arrival) {
        if (!Palace.isInRange(start, arrival)) {
            throw new JanggiGameRuleWarningException("해당 기물은 궁성 내에서만 이동 가능 합니다.");
        }
        return routes.stream()
                .filter(route -> route.canArrive(start, arrival))
                .findFirst()
                .orElseThrow(() -> new JanggiGameRuleWarningException("해당 도착점으로 도착할 수 없는 기물입니다."))
                .retrieveAllPointsOnRoute(start);
    }

    private List<Route> generateDefaultRoutes() {
        final List<Route> routes = new ArrayList<>();
        for (Direction value : Direction.values()) {
            routes.add(new Route(List.of(value)));
        }
        return routes;
    }
}
