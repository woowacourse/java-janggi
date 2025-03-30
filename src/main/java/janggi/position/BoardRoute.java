package janggi.position;

import java.util.Map;

public class BoardRoute {
    private final Map<Position, Directions> boardRoute;

    public BoardRoute(final BoardRouteGenerator boardRouteGenerator) {
        this.boardRoute = boardRouteGenerator.generate();
    }
}
