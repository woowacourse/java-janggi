package domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Palace {
    private static final Set<Position> palacePositions = new HashSet<>(Arrays.asList(
            Position.from(1, 4), Position.from(1,5), Position.from(1,6),
            Position.from(2,4), Position.from(2,5), Position.from(2,6),
            Position.from(3,4), Position.from(3,5), Position.from(3,6),
            Position.from(8,4), Position.from(8,5), Position.from(8,6),
            Position.from(9,4), Position.from(9,5), Position.from(9,6),
            Position.from(10,4), Position.from(10,5), Position.from(10,6)
    ));

    private static final Set<Position> palaceCornerPositions = new HashSet<>(Arrays.asList(
            Position.from(1, 4), Position.from(1,6),
            Position.from(3,4),  Position.from(3,6),
            Position.from(8,4), Position.from(8,6),
            Position.from(10,4), Position.from(10,6)
    ));

    private static final Set<Position> palaceCenterPositions = new HashSet<>(Arrays.asList(
            Position.from(2,5), Position.from(9,5)
    ));

    private Palace() {}

    public static boolean isPalace(Position position) {
        return palacePositions.contains(position);
    }

    public static boolean isPalaceCorner(Position position) {
        return palaceCornerPositions.contains(position);
    }

    public static boolean isPalaceCenter(Position position) {
        return palaceCenterPositions.contains(position);
    }
}
