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
        for (int i = 0; i < route.size() - 1; i++) {
            if (board.hasPiece(route.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Map<Position, List<Position>> convertToPositions(Position position, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();
        for (Route route : routes) {
            List<Position> positionRoute = route.applyDirections(position);
            if (positionRoute.isEmpty()) {
                continue;
            }
            result.put(positionRoute.getLast(), positionRoute);
        }
        return result;
    }
}
