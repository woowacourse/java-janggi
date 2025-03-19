package janggi.piece;

import janggi.Side;
import janggi.board.Position;
import janggi.board.Route;

import java.util.List;

public class Elephant implements Piece {

    private final Side side;

    public Elephant(final Side side) {
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
                computeRouteFixDeltaY(position, -1, -1)
        );
    }

    private static Route computeRouteFixDeltaX(final Position originalPosition, int deltaX, int upDown) {
        Route candidateRoute = new Route();
        Position horizotalMovedPosition = originalPosition.move(deltaX, 0);
        Position diagonalMovedPosition1 = horizotalMovedPosition.move(deltaX, upDown);
        Position diagonalMovedPosition2 = diagonalMovedPosition1.move(deltaX, upDown);

        candidateRoute.addRoute(horizotalMovedPosition);
        candidateRoute.addRoute(diagonalMovedPosition1);
        candidateRoute.addRoute(diagonalMovedPosition2);
        return candidateRoute;
    }

    private static Route computeRouteFixDeltaY(final Position position, int deltaY, int leftRight) {
        Route candidateRoute = new Route();
        Position verticalMovedPosition = position.move(0, deltaY);
        Position diagonalMovedPosition1 = verticalMovedPosition.move(leftRight, deltaY);
        Position diagonalMovedPosition2 = diagonalMovedPosition1.move(leftRight, deltaY);

        candidateRoute.addRoute(verticalMovedPosition);
        candidateRoute.addRoute(diagonalMovedPosition1);
        candidateRoute.addRoute(diagonalMovedPosition2);
        return candidateRoute;
    }

    @Override
    public String getSymbol() {
        return "E";
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
