package janggi.domain.piece.moverules;

import janggi.domain.board.Board;
import janggi.domain.common.Position;
import janggi.domain.piece.Piece;
import janggi.domain.route.Route;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GeneralMoveRule extends CommonMoveRule {

    @Override
    protected boolean canMove(Board board, Piece movePiece, List<Position> route, Position destination) {
        return !hasObstacleOnRoute(board, route) && !isDestinationMyTeam(board, destination, movePiece);
    }

    private boolean hasObstacleOnRoute(Board board, List<Position> route) {
        return route.subList(0, route.size() - 1)
                .stream()
                .anyMatch(board::hasPiece);
    }

    @Override
    protected Map<Position, List<Position>> convertToPositions(Position position, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();
        for (Route route : routes) {
            addValidRoute(position, route, result);
        }
        return result;
    }

    private void addValidRoute(Position position, Route route, Map<Position, List<Position>> result) {
        List<Position> positionRoute = route.applyDirections(position);
        if (positionRoute.isEmpty()) {
            return;
        }
        result.put(positionRoute.getLast(), positionRoute);
    }
}
