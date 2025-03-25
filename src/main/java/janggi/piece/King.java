package janggi.piece;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    private static final int ALLOWED_MOVE = 1;

    public King(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return computeStraightRoutes(position, ALLOWED_MOVE);
    }

    @Override
    public List<Position> filterReachableDestinations(final List<Route> candidateRoutes, final JanggiBoard board) {
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

    @Override
    public String getSymbol() {
        return "G";
    }

}
