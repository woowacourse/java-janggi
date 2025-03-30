package janggi.piece;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {

    private static final int DIAGONAL_COUNT = 2;

    public Elephant(final Side side) {
        super(Symbol.ELEPHANT, side);
    }

    @Override
    public List<Position> filterReachableDestinations(final Position selectedPosition, final JanggiBoard board) {
        List<Route> candidateRoutes = computeDiagonalRoutes(selectedPosition, DIAGONAL_COUNT);
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : candidateRoutes) {
            Position destination = route.getDestination();
            if (board.isOutOfRange(destination)) {
                continue;
            }
            if (board.checkInvalidIntermediatePositions(route)) {
                continue;
            }
            if (isAllyWith(board.findPieceBy(destination))) {
                continue;
            }
            reachablePositions.add(destination);
        }
        return reachablePositions;
    }

}
