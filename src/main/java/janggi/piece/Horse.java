package janggi.piece;

import janggi.board.Direction;
import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public class Horse extends Piece {

    public Horse(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return List.of(
                computeStraightAndDiagonal(position, Direction.RIGHT, Direction.RIGHT_DOWN),
                computeStraightAndDiagonal(position, Direction.RIGHT, Direction.RIGHT_UP),
                computeStraightAndDiagonal(position, Direction.LEFT, Direction.LEFT_DOWN),
                computeStraightAndDiagonal(position, Direction.LEFT, Direction.LEFT_UP),
                computeStraightAndDiagonal(position, Direction.DOWN, Direction.LEFT_DOWN),
                computeStraightAndDiagonal(position, Direction.DOWN, Direction.RIGHT_DOWN),
                computeStraightAndDiagonal(position, Direction.UP, Direction.LEFT_UP),
                computeStraightAndDiagonal(position, Direction.UP, Direction.RIGHT_UP)
        );
    }

    @Override
    public String getSymbol() {
        return "M";
    }

    private Route computeStraightAndDiagonal(final Position position, final Direction straight,
                                             final Direction diagonal) {
        Route candidateRoute = new Route();
        Position straightMovePosition = position.move(straight);
        Position diagonalMovePosition = straightMovePosition.move(diagonal);
        candidateRoute.addRoute(straightMovePosition, diagonalMovePosition);
        return candidateRoute;
    }

}
