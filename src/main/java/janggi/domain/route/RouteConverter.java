package janggi.domain.route;

import janggi.domain.board.Board;
import janggi.domain.common.Position;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteConverter {

    public Map<Position, List<Position>> convertToPosition(Board board, Position position) {
        Piece piece = board.pieceAt(position);
        if (piece.isCha() || piece.isPo()) {
            return convertToContinuousRoutes(position, piece.findRoutes());
        }
        return convertToFixedRoutes(position, piece.findRoutes());
    }

    private Map<Position, List<Position>> convertToFixedRoutes(Position position, List<Route> routes) {
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

    private Map<Position, List<Position>> convertToContinuousRoutes(Position position, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();
        for (Route route : routes) {
            route.applyContinuousDirections(position, result);
        }
        return result;
    }
}
