package janggi.piece;

import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

public class Soldier implements Piece {
    private final Team team;

    public Soldier(Team team) {
        this.team = team;
    }

    @Override
    public List<Route> calculateRoutes(Position position) {
        List<Route> routes = new ArrayList<>();
        List<Direction> directions = Direction.getStraight(team);

        for (Direction direction : directions) {
            addRouteIfCanBePosition(direction, position, routes);
        }
        return routes;
    }

    private void addRouteIfCanBePosition(Direction direction, Position startPoint, List<Route> routes) {
        if (startPoint.canMove(direction)) {
            routes.add(Route.of(List.of(startPoint.move(direction))));
        }
    }
}
