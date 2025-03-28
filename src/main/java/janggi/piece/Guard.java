package janggi.piece;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import java.util.ArrayList;
import java.util.List;

public class Guard extends Piece {

    public Guard(final Side side) {
        super(Symbol.GUARD, side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        List<Position> positions = position.moveToCandidate();
        return Route.createRoutes(positions);
    }

    @Override
    public List<Position> filterReachableDestinations(final List<Route> candidateRoutes, final JanggiBoard board) {
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : candidateRoutes) {
            Position destination = route.getDestination();
            if (destination.isNotPalace()) {
                continue;
            }
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
