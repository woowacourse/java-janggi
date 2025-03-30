package janggi.position;

import java.util.Map;

public class BoardRoute {
    private final Map<Position, Directions> boardRoute;

    public BoardRoute(final BoardRouteGenerator boardRouteGenerator) {
        this.boardRoute = boardRouteGenerator.generate();
    }

    private Directions getDirections(final Position position) {
        final Directions directions = boardRoute.get(position);
        if(directions == null) {
            throw new IllegalArgumentException("해당 위치에 대한 방향이 없습니다.");
        }
        return directions;
    }

    public boolean hasDirection(final Position position, final Direction direction) {
        return getDirections(position).contains(direction);
    }
}
