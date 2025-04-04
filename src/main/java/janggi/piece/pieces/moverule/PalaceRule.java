package janggi.piece.pieces.moverule;

import janggi.board.Palace;
import janggi.position.Direction;
import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

public class PalaceRule implements MoveRule {
    @Override
    public List<Route> moveAll(Position start) {
        List<Route> routes = new ArrayList<>();

        for (Direction direction : Direction.getAllDirection()) {
            addRouteCanBeMove(start, direction, routes);
        }
        return routes;
    }

    private void addRouteCanBeMove(Position position, Direction direction, List<Route> routes) {
        int column = position.getColumn() + direction.getX();
        int row = position.getRow() + direction.getY();

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
}
