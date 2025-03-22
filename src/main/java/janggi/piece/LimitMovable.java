package janggi.piece;

import janggi.board.Position;
import janggi.move.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class LimitMovable implements Piece {

    private final Side side;

    public LimitMovable(Side side) {
        this.side = side;
    }

    @Override
    public List<Position> filterReachableDestinations(final List<Route> routes, final Map<Position, Piece> board) {
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : routes) {
            Position destination = route.getLastPosition();
            if (isInvalidRoute(route, destination, board)) continue;
            reachablePositions.add(destination);
        }
        return reachablePositions;
    }

    private boolean isInvalidRoute(final Route route, final Position destination, final Map<Position, Piece> board) {
        if (destination.isOutOfRange(9, 10) || isAlly(board.get(destination))) {
            return true;
        }
        if (checkInvalidIntermediatePositions(route, board)) {
            return true;
        }
        return isAlly(board.get(destination));
    }

    private boolean checkInvalidIntermediatePositions(final Route route, final Map<Position, Piece> board) {
        for (Position position : route.getIntermediatePositions()) {
            Piece targetPiece = board.get(position);
            if(targetPiece.isOccupied()) {
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
