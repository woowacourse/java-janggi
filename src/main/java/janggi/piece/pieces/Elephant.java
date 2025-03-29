package janggi.piece.pieces;

import janggi.piece.Direction;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

public class Elephant implements Piece {
    public static final int MAX_DEPTH = 3;

    private final Team team;

    public Elephant(Team team) {
        this.team = team;
    }

    @Override
    public List<Route> calculateRoutes(Position start) {
        List<Route> routes = new ArrayList<>();

        findPath(0, Direction.NONE, new ArrayList<>(), start, routes);
        return routes;
    }

    private void findPath(int depth, Direction before, List<Position> route, Position prevPoint, List<Route> routes) {
        if (depth == MAX_DEPTH) {
            routes.add(Route.of(route.stream().toList()));
            return;
        }
        for (Direction direction : before.getNextWithDiagonal()) {
            move(depth, route, prevPoint, routes, direction);
        }
    }

    private void move(int depth, List<Position> route, Position prevPoint, List<Route> routes,
                      Direction direction) {
        if (!Position.isCanBePosition(prevPoint.getColumn() + direction.getX(),
                prevPoint.getRow() + direction.getY())) {
            return;
        }
        Position next = new Position(prevPoint.getColumn() + direction.getX(),
                prevPoint.getRow() + direction.getY());
        route.add(next);
        findPath(depth + 1, direction, route, next, routes);
        route.remove(next);
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
