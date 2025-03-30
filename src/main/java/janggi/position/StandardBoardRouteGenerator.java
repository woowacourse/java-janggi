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
    private static final Position RED_PALACE_POSITION = new Position(5, 2);
    private static final Position BLUE_PALACE_POSITION = new Position(5, 9);

    @Override
    public Map<Position, Directions> generate() {
        final Map<Position, Directions> boardRoute = new HashMap<>();
        boardRoute.putAll(createCardinalRoute());
        boardRoute.putAll(createDiagonalRoute());
        return boardRoute;
    }

    private Map<Position, Directions> createCardinalRoute() {
        final Map<Position, Directions> boardRoute = new HashMap<>();
        for (int x = BOARD_X_LOWER_BOUND; x <= BOARD_X_UPPER_BOUND; ++x) {
            for (int y = BOARD_Y_LOWER_BOUND; y <= BOARD_Y_UPPER_BOUND; ++y) {
                final Position currentPosition = new Position(x, y);
                final Set<Direction> directions = createCardinalMoveableDirections(currentPosition);
                boardRoute.put(currentPosition, new Directions(directions));
            }
        }
        return boardRoute;
    }

    private Set<Direction> createCardinalMoveableDirections(final Position currentPosition) {
        final Set<Direction> moveableDirections = new HashSet<>();
        for (final Direction direction : Direction.getCardinalDirections()) {
            if (currentPosition.isInBounds(direction)) {
                moveableDirections.add(direction);
            }
        }
        return moveableDirections;
    }

    private Map<Position, Directions> createDiagonalRoute() {
        final Map<Position, Directions> boardRoute = new HashMap<>();
        boardRoute.putAll(createDiagonalMoveableDirections(RED_PALACE_POSITION));
        boardRoute.putAll(createDiagonalMoveableDirections(BLUE_PALACE_POSITION));
        return boardRoute;
    }

    private Map<Position, Directions> createDiagonalMoveableDirections(final Position palacePosition) {
        final Map<Position, Set<Direction>> boardRoute = new HashMap<>();
        final Set<Direction> palaceDirections = createCardinalMoveableDirections(palacePosition);
        for (final Direction direction : Direction.getDiagonalDirections()) {
            // NOTE: 왕궁에 대각선 방향을 넣는다.
            palaceDirections.add(direction);

            // 왕궁에서 대각선 위치에 역방향 대각선을 넣는다.
            final Position diagonalPosition = palacePosition.move(direction);
            final Set<Direction> diagonalDirections = createCardinalMoveableDirections(diagonalPosition);
            diagonalDirections.add(direction.reverse());
            boardRoute.put(diagonalPosition, diagonalDirections);
        }
        boardRoute.put(palacePosition, palaceDirections);
        return convertToDirections(boardRoute);
    }

    private Map<Position, Directions> convertToDirections(Map<Position, Set<Direction>> boardRoute) {
        final Map<Position, Directions> convertedBoardRoute = new HashMap<>();
        for (final Map.Entry<Position, Set<Direction>> entry : boardRoute.entrySet()) {
            convertedBoardRoute.put(entry.getKey(), new Directions(entry.getValue()));
        }
        return convertedBoardRoute;
    }
}
