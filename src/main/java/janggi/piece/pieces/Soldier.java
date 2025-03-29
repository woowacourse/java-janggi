package janggi.piece.pieces;

import janggi.piece.Direction;
import janggi.piece.PieceType;
import janggi.piece.Team;
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
    public List<Route> calculateRoutes(Position start) {
        List<Route> routes = new ArrayList<>();
        List<Direction> directions = Direction.getStraight(team);

        for (Direction direction : directions) {
            addRouteIfCanBePosition(direction, start, routes);
        }
        return routes;
    }

    private void addRouteIfCanBePosition(Direction direction, Position startPoint, List<Route> routes) {
        if (startPoint.canMove(direction)) {
            routes.add(Route.of(List.of(startPoint.move(direction))));
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
