package janggi.domain;

import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Placement {

    private final Map<Position, Piece> positionToPieceInRoute;

    public Placement(final Board board, final Position departure, final Position destination) {
        this.positionToPieceInRoute = getRouteWithDestination(departure, destination).stream()
                .filter(board::exists)
                .collect(Collectors.toMap(position -> position, board::getPiece));
    }

    private List<Position> getRouteWithDestination(final Position departure, final Position destination) {
        Route route = Route.of(departure, destination);
        List<Position> routeWithDestination = new ArrayList<>(route.getPositions());
        routeWithDestination.add(destination);
        return Collections.unmodifiableList(routeWithDestination);
    }

    public boolean exists(final Position position) {
        return positionToPieceInRoute.containsKey(position);
    }

    public Piece getPiece(final Position position) {
        if (exists(position)) {
            return positionToPieceInRoute.get(position);
        }
        throw new IllegalArgumentException("배치 내 기물이 존재하지 않습니다.");
    }
}
