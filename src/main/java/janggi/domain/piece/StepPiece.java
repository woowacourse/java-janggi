package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.board.BoardInterface;
import janggi.domain.policy.RoutePolicy;
import java.util.ArrayList;
import java.util.List;

public abstract class StepPiece extends ActivePiece {
    private final List<List<Movement>> moveRange;

    public StepPiece(List<List<Movement>> moveRange, RoutePolicy routePolicy, Side side, PieceType pieceType) {
        super(routePolicy, side, pieceType);
        this.moveRange = moveRange;
    }

    @Override
    public Route findRoute(Position start, Position end) {
        return new Route(moveRange.stream()
                .map(movements -> calculatePath(start, movements))
                .filter(path -> path.getLast().equals(end))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_DESTINATION_MESSAGE)));
    }

    @Override
    public void validateRoute(Route route, BoardInterface boardInterface) {
        if (!routePolicy.isMovable(route, side, boardInterface)) {
            throw new IllegalArgumentException(UNMOVABLE_ROUTE_MESSAGE);
        }
    }

    private List<Position> calculatePath(Position start, List<Movement> path) {
        List<Position> calculatedPath = new ArrayList<>(List.of(start));
        path.forEach(movement -> calculatedPath.add(calculatedPath.getLast().move(movement)));
        return calculatedPath;
    }
}
