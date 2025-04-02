package janggi.domain.piece.limit;

import janggi.domain.Turn;
import janggi.domain.board.Position;
import janggi.domain.move.Route;
import janggi.domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class LimitMovable implements Piece {

    private final Turn side;

    public LimitMovable(final Turn side) {
        this.side = side;
    }

    @Override
    public List<Position> computeReachableDestinations(final Position position, final Map<Position, Piece> board) {
        List<Route> candidateRoutes = computeCandidatePositions(position);

        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : candidateRoutes) {
            reachablePositions.addAll(getReachablePositionIfValid(board, route));
        }
        return reachablePositions;
    }

    abstract List<Route> computeCandidatePositions(final Position position);

    private List<Position> getReachablePositionIfValid(final Map<Position, Piece> board, final Route route) {
        if (isInvalidRoute(route, board)) {
            return List.of();
        }
        return List.of(route.getLastPosition());
    }

    private boolean isInvalidRoute(final Route route, final Map<Position, Piece> board) {
        Position destination = route.getLastPosition();
        if (isAlly(board.get(destination))) {
            return true;
        }
        return checkInvalidIntermediatePositions(route, board);
    }

    private boolean checkInvalidIntermediatePositions(final Route route, final Map<Position, Piece> board) {
        return route.getIntermediatePositions().stream()
                .map(board::get)
                .anyMatch(Piece::isOccupied);
    }

    @Override
    public boolean isCho() {
        return side == Turn.CHO;
    }

    @Override
    public boolean isHan() {
        return side == Turn.HAN;
    }

    @Override
    public Turn getTurn() {
        return side;
    }
}
