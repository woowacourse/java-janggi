package janggi.piece.pieces;

import janggi.piece.Direction;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

public class Horse implements Piece {
    public static final int MAX_DEPTH = 2;

    private final Team team;

    public Horse(Team team) {
        this.team = team;
    }

    @Override
    public List<Route> calculateRoutes(Position position) {
        List<Route> routes = new ArrayList<>();
        findPath(0, MAX_DEPTH, Direction.NONE, new ArrayList<>(), position, routes);
        return routes;
    }

    private void findPath(int depth, int maxDepth, Direction beforeDirection, ArrayList<Position> route,
                          Position beforePosition,
                          List<Route> routes) {
        if (depth == maxDepth) {
            if (route.stream()
                    .allMatch(position -> Position.isCanBePosition(position.getColumn(), position.getRow()))) {
                routes.add(Route.of(route));
            }
            return;
        }
        for (Direction direction : beforeDirection.getNextWithDiagonal()) {
            if (!Position.isCanBePosition(beforePosition.getColumn() + direction.getX(),
                    beforePosition.getRow() + direction.getY())) {
                return;
            }
            Position next = new Position(beforePosition.getColumn() + direction.getX(),
                    beforePosition.getRow() + direction.getY());
            route.add(next);
            findPath(depth + 1, maxDepth, direction, route, next, routes);
            route.remove(next);
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.HORSE;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
