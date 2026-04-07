package janggi.domain.space;

import janggi.domain.Side;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Palace {
    private static final int PALACE_X_MIN = 3;
    private static final int PALACE_X_MAX = 5;
    public static final int CHO_Y_MIN = 0;
    public static final int CHO_Y_MAX = 2;
    public static final int HAN_Y_MIN = 7;
    public static final int HAN_Y_MAX = 9;
    private static final Set<Position> CHO_POSITIONS;
    private static final Set<Position> HAN_POSITIONS;
    private static final Map<Position, List<Direction>> DIAGONALS;

    static {
        Set<Position> choPositions = new HashSet<>();
        for (int x = PALACE_X_MIN; x <= PALACE_X_MAX; x++) {
            addPositions(CHO_Y_MIN, CHO_Y_MAX, choPositions, x);
        }
        CHO_POSITIONS = Collections.unmodifiableSet(choPositions);
        Set<Position> hanPositions = new HashSet<>();
        for (int x = PALACE_X_MIN; x <= PALACE_X_MAX; x++) {
            addPositions(HAN_Y_MIN, HAN_Y_MAX, hanPositions, x);
        }
        HAN_POSITIONS = Collections.unmodifiableSet(hanPositions);
        Map<Position, List<Direction>> diagonals = new HashMap<>();

        diagonals.put(Position.of(3, 0), List.of(Direction.NORTH_EAST));
        diagonals.put(Position.of(5, 0), List.of(Direction.NORTH_WEST));
        diagonals.put(Position.of(3, 2), List.of(Direction.SOUTH_EAST));
        diagonals.put(Position.of(5, 2), List.of(Direction.SOUTH_WEST));
        diagonals.put(Position.of(4, 1), List.of(
                Direction.NORTH_WEST, Direction.NORTH_EAST,
                Direction.SOUTH_WEST, Direction.SOUTH_EAST)
        );

        diagonals.put(Position.of(3, 7), List.of(Direction.NORTH_EAST));
        diagonals.put(Position.of(5, 7), List.of(Direction.NORTH_WEST));
        diagonals.put(Position.of(5, 9), List.of(Direction.SOUTH_WEST));
        diagonals.put(Position.of(3, 9), List.of(Direction.SOUTH_EAST));
        diagonals.put(Position.of(4, 8), List.of(
                Direction.NORTH_WEST, Direction.NORTH_EAST,
                Direction.SOUTH_WEST, Direction.SOUTH_EAST)
        );
        DIAGONALS = Collections.unmodifiableMap(diagonals);
    }

    private static void addPositions(int choYMin, int choYMax, Set<Position> choPositions, int x) {
        for (int y = choYMin; y <= choYMax; y++) {
            choPositions.add(Position.of(x, y));
        }
    }

    private Palace() {
    }

    public static boolean isInside(Position position, Side side) {
        if (side == Side.CHO) {
            return CHO_POSITIONS.contains(position);
        }
        return HAN_POSITIONS.contains(position);
    }

    public static boolean isInsideAny(Position position) {
        return CHO_POSITIONS.contains(position) || HAN_POSITIONS.contains(position);
    }

    public static List<Direction> getDiagonals(Position position) {
        return DIAGONALS.getOrDefault(position, Collections.emptyList());
    }
}
