package janggi.domain.piece.moverules;

import janggi.domain.board.Board;
import janggi.domain.board.Palace;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.Piece;
import janggi.domain.route.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CommonMoveRule implements MoveRule {
    protected boolean isDestinationMyTeam(Board board, Position destination, Piece piece) {
        Piece destinationPiece = board.pieceAt(destination);
        if (board.hasPiece(destination)) {
            return piece.isSameTeam(destinationPiece);
        }
        return false;
    }

    @Override
    public List<Position> findMovablePositions(Board board, Position position, Team team) {
        Piece piece = board.pieceAt(position);

        List<Route> routes = new ArrayList<>(findRoutes(team));
        routes.addAll(addPalaceRoutes(position, team));

        return calculateAvailablePositions(board, piece, convertToPositions(position, routes));
    }

    private List<Position> calculateAvailablePositions(Board board, Piece piece,
                                                       Map<Position, List<Position>> routePositions) {
        return routePositions.entrySet()
                .stream()
                .filter(entry -> canMove(board, piece, entry.getValue(), entry.getKey()))
                .map(Map.Entry::getKey)
                .toList();
    }

    protected List<Route> addPalaceRoutes(Position position, Team team) {
        if (Palace.CHO.isInPalace(position)) {
            return Palace.CHO.findDiagonalRoutes(position);
        }
        if (Palace.HAN.isInPalace(position)) {
            return Palace.HAN.findDiagonalRoutes(position);
        }
        return List.of();
    }

    protected abstract boolean canMove(Board board, Piece movePiece, List<Position> route, Position destination);

    protected abstract Map<Position, List<Position>> convertToPositions(Position position, List<Route> routes);
}
