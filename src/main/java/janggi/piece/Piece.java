package janggi.piece;

import janggi.board.Direction;
import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public abstract class Piece {

    protected static final int MOVE_LIMIT = 10;

    private final Side side;

    protected Piece(final Side side) {
        this.side = side;
    }

    public abstract List<Route> computeCandidatePositions(final Position position);

    public abstract List<Position> filterReachableDestinations(final List<Route> candidateRoutes, final JanggiBoard board);

    public abstract String getSymbol();

    protected List<Route> computeStraightRoutes(final Position position, int distance) {
        return List.of(
                computeStraightLimitRoute(position, Direction.RIGHT, distance),
                computeStraightLimitRoute(position, Direction.LEFT, distance),
                computeStraightLimitRoute(position, Direction.UP, distance),
                computeStraightLimitRoute(position, Direction.DOWN, distance)
        );
    }

    protected List<Route> computeDiagonalRoutes(final Position position, int diagonalCount) {
        return List.of(
                computeStraightAndDiagonal(position, Direction.RIGHT, Direction.RIGHT_DOWN, diagonalCount),
                computeStraightAndDiagonal(position, Direction.RIGHT, Direction.RIGHT_UP, diagonalCount),
                computeStraightAndDiagonal(position, Direction.LEFT, Direction.LEFT_DOWN, diagonalCount),
                computeStraightAndDiagonal(position, Direction.LEFT, Direction.LEFT_UP, diagonalCount),
                computeStraightAndDiagonal(position, Direction.DOWN, Direction.LEFT_DOWN, diagonalCount),
                computeStraightAndDiagonal(position, Direction.DOWN, Direction.RIGHT_DOWN, diagonalCount),
                computeStraightAndDiagonal(position, Direction.UP, Direction.LEFT_UP, diagonalCount),
                computeStraightAndDiagonal(position, Direction.UP, Direction.RIGHT_UP, diagonalCount)
        );
    }

    private Route computeStraightLimitRoute(final Position position, final Direction direction,
                                            final int distanceLimit) {
        Route route = new Route(position.move(direction));
        for (int delta = 1; delta < distanceLimit; delta++) {
            Position destination = route.getDestination();
            route.addRoute(destination.move(direction));
        }
        return route;
    }

    private Route computeStraightAndDiagonal(final Position position, final Direction straight,
                                             final Direction diagonal, int diagonalCount) {
        Route route = new Route(position.move(straight));
        for (int count = 0; count < diagonalCount; count++) {
            Position destination = route.getDestination();
            route.addRoute(destination.move(diagonal));
        }
        return route;
    }

    public boolean isAllyWith(final Piece anotherPiece) {
        if (isCho()) {
            return anotherPiece.isCho();
        }
        if (isHan()) {
            return anotherPiece.isHan();
        }
        throw new IllegalStateException("[ERROR] 프로그램에 오류가 발생했습니다.");
    }

    public boolean isEnemyWith(final Piece anotherPiece) {
        return !isAllyWith(anotherPiece);
    }

    public boolean isCho() {
        return side == Side.CHO;
    }

    public boolean isHan() {
        return side == Side.HAN;
    }

}
