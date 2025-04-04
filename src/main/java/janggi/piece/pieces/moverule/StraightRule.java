package janggi.piece.pieces.moverule;

import janggi.board.Palace;
import janggi.piece.Team;
import janggi.position.Direction;
import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

public class StraightRule implements MoveRule {
    private final Team team;

    public StraightRule(Team team) {
        this.team = team;
    }

    @Override
    public List<Route> moveAll(Position start) {
        List<Route> routes = new ArrayList<>();

        for (Direction direction : team.getTeamDirection()) {
            addRouteIfCanBePosition(direction, start, routes);
        }
        return routes;
    }

    private void addRouteIfCanBePosition(Direction direction, Position startPoint, List<Route> routes) {
        if (direction.isDiagonal()) {
            addIfCanMoveDiagonal(direction, startPoint, routes);
            return;
        }
        if (startPoint.canMove(direction)) {
            routes.add(Route.of(List.of(startPoint.move(direction))));
        }
    }

    private static void addIfCanMoveDiagonal(Direction direction, Position startPoint, List<Route> routes) {
        if (Palace.canDiagonalInPalace(startPoint)) {
            routes.add(Route.of(List.of(startPoint.move(direction))));
        }
    }
}
