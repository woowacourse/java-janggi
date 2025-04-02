package janggi.domain.piece.unlimit;

import janggi.domain.Turn;
import janggi.domain.board.Position;
import janggi.domain.move.Direction;
import janggi.domain.move.Route;
import janggi.domain.piece.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class UnLimitMovable implements Piece {
    private static final int MOVE_LIMIT = 10;

    private final Turn side;

    public UnLimitMovable(Turn side) {
        this.side = side;
    }

    @Override
    public List<Position> computeReachableDestinations(final Position position, final Map<Position, Piece> board) {
        List<Route> candidateRoutes = computeCandidateDirections(position);
        List<Position> reachableDestinations = new ArrayList<>();
        for (Route route : candidateRoutes) {
            List<Position> positions = route.getPositions();
            reachableDestinations.addAll(addValidDestination(positions, board));
        }
        return reachableDestinations;
    }

    public List<Route> computeCandidateDirections(final Position position) {
        List<Route> movableDirections = new ArrayList<>(Arrays.asList(createCandidateDirections(position, Direction.UP),
                    createCandidateDirections(position, Direction.DOWN),
                    createCandidateDirections(position, Direction.LEFT),
                    createCandidateDirections(position, Direction.RIGHT)));

        if(position.isDiagonalMovable()) {
            movableDirections.addAll(computeCandidateDirectionsInPalace(position));
        }
        movableDirections.removeIf(route -> route.getPositions().isEmpty());
        return movableDirections;
    }

    protected abstract List<Position> addValidDestination(final List<Position> positions, final Map<Position, Piece> board);

    private Route createCandidateDirections(final Position position, final Direction direction) {
        Route route = new Route(position);

        for (int i = 0; i < MOVE_LIMIT; i++) {
            Position lastPosition = route.getLastPosition();
            Position movedPosition = lastPosition.move(direction);

            if (movedPosition.isInBoardRange()) {
                route.addRoute(lastPosition.move(direction));
            }
        }
        route.deleteFirstPosition();
        return route;
    }

    private List<Route> computeCandidateDirectionsInPalace(final Position position) {
        return new ArrayList<>(Arrays.asList(createPalaceRoute(position, Direction.LEFT_UP),
                createPalaceRoute(position, Direction.LEFT_DOWN),
                createPalaceRoute(position, Direction.RIGHT_UP),
                createPalaceRoute(position, Direction.RIGHT_DOWN)));
    }

    private Route createPalaceRoute(final Position position, final Direction direction) {
        Route route = new Route(position);

        for (int i = 0; i < MOVE_LIMIT; i++) {
            Position lastPosition = route.getLastPosition();
            Position movedPosition = lastPosition.move(direction);
            if (movedPosition.isDiagonalMovable()) {
                route.addRoute(movedPosition);
            }
        }
        route.deleteFirstPosition();
        return route;
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
