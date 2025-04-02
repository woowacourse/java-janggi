package janggi.domain.piece.limit;

import janggi.domain.Turn;
import janggi.domain.board.Position;
import janggi.domain.move.Direction;
import janggi.domain.move.Route;
import janggi.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Soldier extends LimitMovable {

    public Soldier(final Turn side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        if (isCho()) {
            return moveCho(position);
        }
        return moveHan(position);
    }

    private List<Route> moveCho(final Position position) {
        List<Route> movableRoute = new ArrayList<>();
        movableRoute.addAll(createRoute(position, Direction.LEFT));
        movableRoute.addAll(createRoute(position, Direction.UP));
        movableRoute.addAll(createRoute(position, Direction.RIGHT));

        if (position.isDiagonalMovable()) {
            movableRoute.addAll(createRouteInPalace(position, Direction.LEFT_UP));
            movableRoute.addAll(createRouteInPalace(position, Direction.RIGHT_UP));
        }

        return movableRoute;
    }

    private List<Route> moveHan(final Position position) {
        List<Route> movableRoute = new ArrayList<>();
        movableRoute.addAll(createRoute(position, Direction.LEFT));
        movableRoute.addAll(createRoute(position, Direction.DOWN));
        movableRoute.addAll(createRoute(position, Direction.RIGHT));

        if (position.isDiagonalMovable()) {
            movableRoute.addAll(createRouteInPalace(position, Direction.LEFT_DOWN));
            movableRoute.addAll(createRouteInPalace(position, Direction.RIGHT_DOWN));
        }

        return movableRoute;
    }

    private List<Route> createRoute(final Position position, final Direction direction) {
        Position movedPosition = position.move(direction);
        if(movedPosition.isInBoardRange()) {
            return List.of(new Route(movedPosition));
        }
        return List.of();
    }

    private List<Route> createRouteInPalace(final Position position, final Direction direction) {
        Position movedPosition = position.move(direction);
        if (movedPosition.isDiagonalMovable()){
            return List.of(new Route(movedPosition));
        }
        return List.of();
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }
}
