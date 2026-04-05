package janggi.domain.piece.moverules;

import janggi.domain.board.Board;
import janggi.domain.common.Position;
import janggi.domain.piece.Piece;
import java.util.List;

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
}
