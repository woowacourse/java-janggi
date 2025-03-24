package janggi.piece.limit;

import janggi.board.Position;
import janggi.move.Route;
import janggi.piece.Piece;
import janggi.piece.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class LimitMovable implements Piece {

    private final Side side;

    public LimitMovable(final Side side) {
        this.side = side;
    }

    @Override
    public List<Position> computeReachableDestinations(final Position position, final Map<Position, Piece> board) {
        List<Route> candidateRoutes = computeCandidatePositions(position);

        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : candidateRoutes) {
            if (isInvalidRoute(route, board)) {
                continue;
            }
            reachablePositions.add(route.getLastPosition());
        }
        return reachablePositions;
    }

    abstract List<Route> computeCandidatePositions(final Position position);

    private boolean isInvalidRoute(final Route route, final Map<Position, Piece> board) {
        Position destination = route.getLastPosition();
        if (destination.isOutOfRange() || isAlly(board.get(destination))) {
            return true;
        }
        return checkInvalidIntermediatePositions(route, board);
    }

    private boolean checkInvalidIntermediatePositions(final Route route, final Map<Position, Piece> board) {
        for (Position position : route.getIntermediatePositions()) {
            Piece targetPiece = board.get(position);
            if (targetPiece.isOccupied()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCho() {
        return side == Side.CHO;
    }

    @Override
    public boolean isHan() {
        return side == Side.HAN;
    }

}
