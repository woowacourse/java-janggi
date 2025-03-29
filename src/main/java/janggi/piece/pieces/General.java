package janggi.piece.pieces;

import janggi.board.Palace;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Direction;
import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

public class General implements Piece {
    private final Team team;

    public General(Team team) {
        this.team = team;
    }

    @Override
    public List<Route> calculateRoutes(Position position) {
        List<Route> routes = new ArrayList<>();

        for (Direction direction : Direction.getAllDirection()) {
            applyGeneralProperty(position, direction, routes);
        }
        return routes;
    }

    private static void applyGeneralProperty(Position position, Direction direction, List<Route> routes) {
        int column = direction.moveColumn(position.getColumn());
        int row = direction.moveColumn(position.getRow());
        if (!Palace.isInPalace(column, row)) {
            return;
        }
        if (direction.idDiagonal() && !Palace.canDiagonalInPalace(column, row)) {
            return;
        }
        routes.add(Route.of(List.of(new Position(column, row))));
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
