package janggi.piece;

import janggi.board.Direction;
import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    protected static final int MOVE_LIMIT = 10;

    private final Symbol symbol;
    private final Side side;

    protected Piece(final Symbol symbol, final Side side) {
        this.symbol = symbol;
        this.side = side;
    }

    public abstract List<Position> filterReachableDestinations(final Position selectedPosition,
                                                               final JanggiBoard board);

    protected List<Route> computeStraightRoutes(final Position position, int distance) {
        List<Route> candidateRoutes = new ArrayList<>();
        for (Direction direction : position.getCandidateDirections()) {
            candidateRoutes.add(computeStraightLimitRoute(position, direction, distance));
        }
        return candidateRoutes;
    }

    protected List<Route> computeDiagonalRoutes(final Position position, int diagonalCount) {
        return List.of(
                computeStraightAndDiagonal(position, Direction.RIGHT, Direction.DOWN_RIGHT, diagonalCount),
                computeStraightAndDiagonal(position, Direction.RIGHT, Direction.UP_RIGHT, diagonalCount),
                computeStraightAndDiagonal(position, Direction.LEFT, Direction.DOWN_LEFT, diagonalCount),
                computeStraightAndDiagonal(position, Direction.LEFT, Direction.UP_LEFT, diagonalCount),
                computeStraightAndDiagonal(position, Direction.DOWN, Direction.DOWN_LEFT, diagonalCount),
                computeStraightAndDiagonal(position, Direction.DOWN, Direction.DOWN_RIGHT, diagonalCount),
                computeStraightAndDiagonal(position, Direction.UP, Direction.UP_LEFT, diagonalCount),
                computeStraightAndDiagonal(position, Direction.UP, Direction.UP_RIGHT, diagonalCount)
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

    public boolean isSameSide(final Side side) {
        return this.side == side;
    }

    public boolean isKing() {
        return symbol == Symbol.KING;
    }

    public boolean isCannon() {
        return symbol == Symbol.CANNON;
    }

    public boolean isEmpty() {
        return symbol == Symbol.EMPTY;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Side getSide() {
        return side;
    }

    public int getScore() {
        return symbol.getScore();
    }

}
