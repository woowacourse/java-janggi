package janggi.piece.pieces;

import janggi.board.Palace;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Direction;
import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

public record General(Team team) implements Piece {
    @Override
    public List<Route> calculateRoutes(Position position) {
        List<Route> routes = new ArrayList<>();

        List<Direction> allDirection = Direction.getAllDirection();
        for (Direction direction : allDirection) {
            applyGeneralProperty(position, direction, routes);
        }
        return routes;
    }

    private static void applyGeneralProperty(Position position, Direction direction, List<Route> routes) {
        int column = direction.moveColumn(position.getColumn());
        int row = direction.moveRow(position.getRow());
        if (position.isSamePoint(new Position(column, row))) {
            return;
        }
        if (!Palace.isInPalace(column, row)) {
            return;
        }
        if (direction.idDiagonal() && !Palace.canDiagonalInPalace(position.getColumn(), position.getRow())) {
            return;
        }
        routes.add(Route.of(List.of(new Position(column, row))));
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
