package janggi.domain.piece.moverules;

import janggi.domain.board.Board;
import janggi.domain.common.Direction;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.Piece;
import janggi.domain.route.Route;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoMoveRule extends CommonMoveRule {

    @Override
    public List<Route> findRoutes(Team team) {
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.RIGHT));
        Route route3 = new Route(List.of(Direction.DOWN));
        Route route4 = new Route(List.of(Direction.LEFT));
        return List.of(route1, route2, route3, route4);
    }

    @Override
    protected boolean canMove(Board board, Piece movePiece, List<Position> route, Position destination) {
        return hasOneObstacleAndNotPo(board, route) && !isDestinationMyTeam(board, destination, movePiece)
                && !isDestinationPo(board, destination);
    }

    private boolean isDestinationPo(Board board, Position destination) {
        Piece destinationPiece = board.pieceAt(destination);
        if (board.hasPiece(destination)) {
            return destinationPiece.isPo();
        }
        return false;
    }

    private boolean hasOneObstacleAndNotPo(Board board, List<Position> route) {
        List<Piece> obstacles = route.subList(0, route.size() - 1)
                .stream()
                .filter(board::hasPiece)
                .map(board::pieceAt)
                .toList();
        return obstacles.size() == 1 && !obstacles.getFirst().isPo();
    }

    @Override
    protected Map<Position, List<Position>> convertToPositions(Position position, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();
        for (Route route : routes) {
            route.applyContinuousDirections(position, result);
        }
        return result;
    }
}
