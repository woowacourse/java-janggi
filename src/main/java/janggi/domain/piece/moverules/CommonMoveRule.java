package janggi.domain.piece.moverules;

import janggi.domain.board.Board;
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

        List<Position> availablePositions = calculateAvailablePositions(board, piece,
                convertToPositions(position, findRoutes(team)));
        
        return availablePositions;
    }

    private List<Position> calculateAvailablePositions(Board board, Piece piece,
                                                       Map<Position, List<Position>> routePositions) {
        List<Position> result = new ArrayList<>();
        for (Map.Entry<Position, List<Position>> entry : routePositions.entrySet()) {
            Position destination = entry.getKey();
            List<Position> route = entry.getValue();

            if (canMove(board, piece, route, destination)) {
                result.add(destination);
            }
        }
        return result;
    }

    protected abstract boolean canMove(Board board, Piece movePiece, List<Position> route, Position destination);

    protected abstract Map<Position, List<Position>> convertToPositions(Position position, List<Route> routes);
}
