package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;

import java.util.List;

public class Horse implements Piece {

    private final Side side;

    public Horse(final Side side) {
        this.side = side;
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return List.of(
                computeRouteFixDeltaX(position, 1, 1),
                computeRouteFixDeltaX(position, 1, -1),
                computeRouteFixDeltaX(position, -1, 1),
                computeRouteFixDeltaX(position, -1, -1),

                computeRouteFixDeltaY(position, 1, 1),
                computeRouteFixDeltaY(position, 1, -1),
                computeRouteFixDeltaY(position, -1, 1),
                computeRouteFixDeltaY(position, -1, -1));
    }

    private static Route computeRouteFixDeltaX(final Position originalPosition, int deltaX, int upDown) {
        Route candidateRoute = new Route();
        Position horizontalMovedPosition = originalPosition.move(deltaX, 0);
        Position diagonalMovedPosition = horizontalMovedPosition.move(deltaX, upDown);

        candidateRoute.addRoute(horizontalMovedPosition);
        candidateRoute.addRoute(diagonalMovedPosition);
        return candidateRoute;
    }

    private static Route computeRouteFixDeltaY(final Position originalPosition, int deltaY, int leftRight) {
        Route candidateRoute = new Route();
        Position horizontalMovedPosition = originalPosition.move(0, deltaY);
        Position diagonalMovedPosition = horizontalMovedPosition.move(leftRight, deltaY);

        candidateRoute.addRoute(horizontalMovedPosition);
        candidateRoute.addRoute(diagonalMovedPosition);
        return candidateRoute;
    }

    @Override
    public String getSymbol() {
        return "M";
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
