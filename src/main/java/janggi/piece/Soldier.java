package janggi.piece;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;

import java.util.List;

public class Soldier implements Piece {

    private final Side side;

    public Soldier(final Side side) {
        this.side = side;
    }

    @Override
    public List<Route> computeCandidatePositions(Position position) {
        if (isCho()) {
            return moveCho(position);
        }
        return moveHan(position);
    }

    private List<Route> moveCho(final Position position) {
        return List.of(
                createRoute(position, Direction.LEFT),
                createRoute(position, Direction.UP),
                createRoute(position, Direction.RIGHT)
        );
    }

    private List<Route> moveHan(final Position position) {
        return List.of(
                createRoute(position, Direction.LEFT),
                createRoute(position, Direction.DOWN),
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
        return "J";
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
