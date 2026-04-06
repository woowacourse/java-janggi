package domain.board;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Palace {
    private static final int PALACE_MIN_X = 4;
    private static final int PALACE_MAX_X = 6;
    private static final int HAN_PALACE_MIN_Y = 1;
    private static final int HAN_PALACE_MAX_Y = 3;
    private static final int CHO_PALACE_MIN_Y = 8;
    private static final int CHO_PALACE_MAX_Y = 10;

    private final Map<PositionPair, List<Position>> diagonalPaths = Map.ofEntries(
            Map.entry(makePair(4, 1, 5, 2), path()),
            Map.entry(makePair(5, 2, 6, 3), path()),
            Map.entry(makePair(4, 1, 6, 3), path(5, 2)),

            Map.entry(makePair(6, 1, 5, 2), path()),
            Map.entry(makePair(5, 2, 4, 3), path()),
            Map.entry(makePair(6, 1, 4, 3), path(5, 2)),

            Map.entry(makePair(4, 8, 5, 9), path()),
            Map.entry(makePair(5, 9, 6, 10), path()),
            Map.entry(makePair(4, 8, 6, 10), path(5, 9)),

            Map.entry(makePair(6, 8, 5, 9), path()),
            Map.entry(makePair(5, 9, 4, 10), path()),
            Map.entry(makePair(6, 8, 4, 10), path(5, 9))
    );

    public boolean contains(Position position) {
        return isInsidePalaceX(position) && isInsidePalaceY(position);
    }

    private boolean isInsidePalaceX(Position position) {
        return position.x() >= PALACE_MIN_X
                && position.x() <= PALACE_MAX_X;
    }

    private boolean isInsidePalaceY(Position position) {
        return isInsideHanPalaceY(position) || isInsideChoPalaceY(position);
    }

    private boolean isInsideHanPalaceY(Position position) {
        return position.y() >= HAN_PALACE_MIN_Y
                && position.y() <= HAN_PALACE_MAX_Y;
    }

    private boolean isInsideChoPalaceY(Position position) {
        return position.y() >= CHO_PALACE_MIN_Y
                && position.y() <= CHO_PALACE_MAX_Y;
    }

    Optional<List<Position>> findDiagonalPath(Position from, Position to) {
        return Optional.ofNullable(diagonalPaths.get(new PositionPair(from, to)));
    }

    private PositionPair makePair(int fromX, int fromY, int toX, int toY) {
        return new PositionPair(
                new Position(fromX, fromY),
                new Position(toX, toY)
        );
    }

    private List<Position> path() {
        return List.of();
    }

    private List<Position> path(int x, int y) {
        return List.of(new Position(x, y));
    }
}
