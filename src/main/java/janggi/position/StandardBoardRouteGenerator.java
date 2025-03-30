package janggi.position;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StandardBoardRouteGenerator implements BoardRouteGenerator {
    private static final int BOARD_X_LOWER_BOUND = 1;
    private static final int BOARD_X_UPPER_BOUND = 9;
    private static final int BOARD_Y_LOWER_BOUND = 1;
    private static final int BOARD_Y_UPPER_BOUND = 10;

    @Override
    public Map<Position, Directions> generate() {
        final Map<Position, Directions> boardRoute = new HashMap<>();
        boardRoute.putAll(createCardinalRoute());
        return boardRoute;
    }

    private Map<Position, Directions> createCardinalRoute() {
        final Map<Position, Directions> boardRoute = new HashMap<>();
        for(int x = BOARD_X_LOWER_BOUND; x <= BOARD_X_UPPER_BOUND; ++x) {
            for(int y = BOARD_Y_LOWER_BOUND; y <= BOARD_Y_UPPER_BOUND; ++y) {
                final Position currentPosition = new Position(x, y);
                final Set<Direction> directions = createCardinalMoveableDirections(currentPosition);
                boardRoute.put(currentPosition, new Directions(directions));
            }
        }
        return boardRoute;
    }

    private Set<Direction> createCardinalMoveableDirections(final Position currentPosition) {
        final Set<Direction> moveableDirections = new HashSet<>();
        for(final Direction direction : Direction.getCardinalDirections()) {
            if(currentPosition.isInBounds(direction)) {
                moveableDirections.add(direction);
            }
        }
        return moveableDirections;
    }
}
