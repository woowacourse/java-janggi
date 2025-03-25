package domain.piece.route.routeselector;

import domain.MovingPattern;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;
import java.util.List;

public class SoldierRouteSelector implements JanggiPieceRouteSelector {

    private static final List<MovingPattern> SOLDIER_OF_CHO_DIRECTIONS = List.of(
            MovingPattern.MOVE_RIGHT,
            MovingPattern.MOVE_LEFT,
            MovingPattern.MOVE_UP,
            MovingPattern.MOVE_DIAGONAL_UP_RIGHT,
            MovingPattern.MOVE_DIAGONAL_UP_LEFT
    );
    private static final List<MovingPattern> SOLDIER_OF_HAN_DIRECTIONS = List.of(
            MovingPattern.MOVE_RIGHT,
            MovingPattern.MOVE_LEFT,
            MovingPattern.MOVE_DOWN,
            MovingPattern.MOVE_DIAGONAL_DOWN_LEFT,
            MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT
    );

    @Override
    public List<MovingPattern> getRoute(final JanggiSide side, final List<List<MovingPattern>> routes, final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        List<MovingPattern> patterns = routes.stream()
                .filter(route -> {
                    if (beforePosition.canMove(route)) {
                        JanggiPosition newPosition = beforePosition.move(route);
                        return newPosition.equals(afterPosition);
                    }
                    return false;
                })
                .findFirst()
                .orElseThrow(InvalidPathException::new);

        checkValidRouteOfSide(side, patterns);
        return patterns;
    }


    private void checkValidRouteOfSide(final JanggiSide side, final List<MovingPattern> patterns) {
        if (side == JanggiSide.CHO && !SOLDIER_OF_CHO_DIRECTIONS.contains(patterns.getFirst())) {
            throw new InvalidPathException();
        }

        if (side == JanggiSide.HAN && !SOLDIER_OF_HAN_DIRECTIONS.contains(patterns.getFirst())) {
            throw new InvalidPathException();
        }
    }
}
