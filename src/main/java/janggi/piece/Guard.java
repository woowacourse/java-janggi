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
    public List<Position> filterReachableDestinations(final Position selectedPosition, final JanggiBoard board) {
        List<Position> positions = selectedPosition.moveToCandidate();
        List<Route> candidateRoutes = Route.createRoutes(positions);
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
