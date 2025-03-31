package janggi.piece;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(final Side side) {
        super(Symbol.CHARIOT, side);
    }

    @Override
    public List<Position> filterReachableDestinations(final Position selectedPosition, final JanggiBoard board) {
        List<Route> candidateRoutes = computeStraightRoutes(selectedPosition, MOVE_LIMIT);
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : candidateRoutes) {
            computeReachablePositions(board, route, reachablePositions);
        }
        return reachablePositions;
    }

    private void computeReachablePositions(final JanggiBoard board, final Route route,
                                           final List<Position> reachablePositions) {
        for (Position position : route.getPositions()) {
            if (wouldStop(board, position)) {
                break;
            }
            if (couldCatchEnemyPiece(board, position)) {
                reachablePositions.add(position);
                break;
            }
            reachablePositions.add(position);
        }
    }

    private boolean wouldStop(final JanggiBoard board, final Position position) {
        return board.isOutOfRange(position) || isAllyWith(board.findPieceBy(position));
    }

    private boolean couldCatchEnemyPiece(final JanggiBoard board, final Position position) {
        return board.isPositionHasPiece(position) && isEnemyWith(board.findPieceBy(position));
    }

}
