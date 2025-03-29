package janggi.piece.pieces;

import janggi.board.Palace;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Direction;
import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

public record Scholar(Team team) implements Piece {
    @Override
    public List<Route> calculateRoutes(Position position) {
        List<Route> routes = new ArrayList<>();

        for (Direction direction : Direction.getAllDirection()) {
            addRouteCanBeMove(position, direction, routes);
        }
        return routes;
    }

    private void addRouteCanBeMove(Position position, Direction direction, List<Route> routes) {
        int column = direction.moveColumn(position.getColumn());
        int row = direction.moveRow(position.getRow());

        if (!Position.isCanBePosition(column, row)) {
            return;
        }
        Position nextPosition = new Position(column, row);
        if (position.isSamePoint(nextPosition)) {
            return;
        }
        if (!Palace.isInPalace(nextPosition)) {
            return;
        }
        if (direction.isDiagonal() && !Palace.canDiagonalInPalace(position)) {
            return;
        }
        routes.add(Route.of(List.of(nextPosition)));
    }

    @Override
    public PieceType getType() {
        return PieceType.SCHOLAR;
    }
}
