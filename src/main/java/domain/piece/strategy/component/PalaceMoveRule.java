package domain.piece.strategy.component;

import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class PalaceMoveRule {

    private static final int CHO_PALACE_START_ROW = 1;
    private static final int CHO_PALACE_END_ROW = 3;

    private static final int HAN_PALACE_START_ROW = 8;
    private static final int HAN_PALACE_END_ROW = 10;

    private static final int PALACE_START_COLUMN = 4;
    private static final int PALACE_END_COLUMN = 6;

    private static final int[] D_ROW = {1, -1, 0, 0};
    private static final int[] D_COLUMN = {0, 0, -1, 1};

    private static final Map<Position, List<Position>> DIAGONAL_EDGES = Map.of(
            Position.of(1, 4), List.of(Position.of(2, 5)),
            Position.of(1, 6), List.of(Position.of(2, 5)),
            Position.of(2, 5), List.of(Position.of(1, 4), Position.of(1, 6), Position.of(3, 4), Position.of(3, 6)),
            Position.of(3, 4), List.of(Position.of(2, 5)),
            Position.of(3, 6), List.of(Position.of(2, 5)),

            Position.of(8, 4), List.of(Position.of(9, 5)),
            Position.of(8, 6), List.of(Position.of(9, 5)),
            Position.of(9, 5), List.of(Position.of(8, 4), Position.of(8, 6), Position.of(10, 4), Position.of(10, 6)),
            Position.of(10, 4), List.of(Position.of(9, 5)),
            Position.of(10, 6), List.of(Position.of(9, 5))
    );

    public boolean isPalacePath(Position start, Position destination) {
        if (isNotInPalace(start) || isNotInPalace(destination)) {
            return false;
        }

        boolean isStraightPath = IntStream.range(0, D_ROW.length)
                .anyMatch(index -> isEqualToDestination(start, destination, index));

        boolean isDiagonalPath = isDiagonalPath(start, destination);

        return (isStraightPath || isDiagonalPath);
    }

    private boolean isNotInPalace(Position position) {
        boolean isRowOfChoInRange = position.isRowInRange(CHO_PALACE_START_ROW, CHO_PALACE_END_ROW);
        boolean isRowOfHanInRange = position.isRowInRange(HAN_PALACE_START_ROW, HAN_PALACE_END_ROW);
        boolean isColumnInRange = position.isColumnInRange(PALACE_START_COLUMN, PALACE_END_COLUMN);

        boolean isRowInRange = (isRowOfChoInRange || isRowOfHanInRange);

        return !isRowInRange || !isColumnInRange;
    }

    private boolean isEqualToDestination(Position start, Position destination, int i) {
        Position changedPosition = start.go(D_ROW[i], D_COLUMN[i]);
        return changedPosition.equals(destination);
    }

    private boolean isDiagonalPath(Position start, Position end) {
        return DIAGONAL_EDGES.getOrDefault(start, List.of())
                .contains(end);
    }
}
