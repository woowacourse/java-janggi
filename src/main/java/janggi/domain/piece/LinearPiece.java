package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.policy.RoutePolicy;
import java.util.Set;
import java.util.stream.Stream;

public abstract class LinearPiece extends ActivePiece {
    private static final Integer GUNG_SUNG_COL_START = 4;
    private static final Integer GUNG_SUNG_COL_END = 6;
    private static final Integer HAN_GUNG_SUNG_ROW_START = 1;
    private static final Integer HAN_GUNG_SUNG_ROW_END = 3;
    private static final Integer CHO_GUNG_SUNG_ROW_START = 8;
    private static final Integer CHO_GUNG_SUNG_ROW_END = 10;

    private static final Set<Position> GungSungOrthogonalDirections = Set.of(
            new Position(1, 5), new Position(2, 4), new Position(2, 6), new Position(3,5),
            new Position(8, 5), new Position(9, 4), new Position(9, 6), new Position(10,5)
    );

    public LinearPiece(RoutePolicy routePolicy, Side side, PieceType pieceType) {
        super(routePolicy, side, pieceType);
    }

    @Override
    public Route findRoute(Position start, Position end) {
        if(!isLinear(start, end)) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }

        Movement direction = getLinearDirection(start, end);
        int distance = calculateLinearDistance(start, end);

        return calculatePath(start, direction, distance);
    }

    protected boolean isLinear(Position start, Position end) {
        if(start.compareX(end) == 0 && start.compareY(end) == 0) {
            return false;
        }

        int dx = start.getDeltaX(end);
        int dy = start.getDeltaY(end);

        return dx == 0 || dy == 0 || isGungSungDiagonal(start, end);
    }

    private boolean isGungSungDiagonal(Position start, Position end ) {
        return isGungSung(start) && isGungSung(end) && (!GungSungOrthogonalDirections.contains(start) && !GungSungOrthogonalDirections.contains(end));
    }

    protected static boolean isGungSung(Position position) {
        return position.isRange(HAN_GUNG_SUNG_ROW_START, HAN_GUNG_SUNG_ROW_END, GUNG_SUNG_COL_START, GUNG_SUNG_COL_END)
                || position.isRange(CHO_GUNG_SUNG_ROW_START, CHO_GUNG_SUNG_ROW_END, GUNG_SUNG_COL_START, GUNG_SUNG_COL_END);
    }

    protected Route calculatePath(Position start, Movement direction, int dist) {
        return new Route(Stream
                .iterate(start, current -> current.move(direction))
                .limit(Math.abs(dist) + 1)
                .toList());
    }

    protected Movement getLinearDirection(Position start, Position end) {
        int dx = start.compareX(end);
        int dy = start.compareY(end);

        return Movement.of(dx, dy);
    }

    protected int calculateLinearDistance(Position start, Position end) {
        int dx = start.getDeltaX(end);
        int dy = start.getDeltaY(end);

        return Math.max(dx, dy);
    }
}
