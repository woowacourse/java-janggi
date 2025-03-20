package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;

import java.util.List;

public class Cannon implements Piece {

    private static final int MOVE_LIMIT = 10;

    private final Side side;

    public Cannon(final Side side) {
        this.side = side;
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        Route rightRoute = new Route();
        Route leftRoute = new Route();
        Route upRoute = new Route();
        Route downRoute = new Route();

        for (int delta = 1; delta <= MOVE_LIMIT; delta++) {
            rightRoute.addRoute(position.move(delta, 0));
            leftRoute.addRoute(position.move(-delta, 0));
            upRoute.addRoute(position.move(0, delta));
            downRoute.addRoute(position.move(0, -delta));
        }

        return List.of(rightRoute, leftRoute, upRoute, downRoute);
    }

    @Override
    public String getSymbol() {
        return "P";
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
