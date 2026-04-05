package janggi.domain.board;

import janggi.domain.Position;
import java.util.List;
import java.util.Set;

public class Palace {
    private static final List<PalaceRange> RANGES = List.of(
            new PalaceRange(1, 3, 4, 6),
            new PalaceRange(8, 10, 4, 6)
    );

    private static final Set<Position> ORTHOGONAL_POSITIONS = Set.of(
            new Position(1, 5), new Position(2, 4), new Position(2, 6), new Position(3, 5),
            new Position(8, 5), new Position(9, 4), new Position(9, 6), new Position(10, 5)
    );


    public static boolean isDiagonalMove(Position start, Position end) {
        if (!isInside(start) || !isInside(end) || !start.isDiagonal(end)) {
            return false;
        }

        return !ORTHOGONAL_POSITIONS.contains(start) && !ORTHOGONAL_POSITIONS.contains(end);    }

    public static boolean isInside(Position position) {
        return RANGES.stream().anyMatch(range -> range.isContain(position));
    }
}
