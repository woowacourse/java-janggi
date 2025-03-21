package janggi.piece;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;

import java.util.List;

public class King implements Piece {

    private final Side side;

    public King(final Side side) {
        this.side = side;
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return List.of(
                createRoute(position, Direction.UP),
                createRoute(position, Direction.RIGHT),
                createRoute(position, Direction.LEFT),
                createRoute(position, Direction.RIGHT)
        );
    }

    private Route createRoute(final Position position, final Direction direction) {
        Route route = new Route();
        route.addRoute(position.move(direction));
        return route;
    }

    @Override
    public String getSymbol() {
        return "G";
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
