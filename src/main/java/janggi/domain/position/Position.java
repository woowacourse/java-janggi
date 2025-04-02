package janggi.domain.position;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {

    private static final int Y_MIN_THRESHOLD = 1;
    private static final int Y_MAX_THRESHOLD = 10;
    private static final int X_MIN_THRESHOLD = 1;
    private static final int X_MAX_THRESHOLD = 9;
    private static final List<Position> POSITIONS = initialize();
    private static final List<Integer> X_POINTS_IN_GUNGSUNG = List.of(4, 5, 6);
    private static final List<List<Integer>> Y_POINTS_IN_GUNGSUNG_EACH_TEAM = List.of(
            List.of(1, 2, 3),
            List.of(8, 9, 10)
    );
    private static final List<Position> GUNGSUNG_POSITION = initializeGungSung();
    private static final List<Position> CAN_DIAGONAL_POSITION = List.of(
            new Position(1, 4), new Position(3, 4), new Position(2, 5),
            new Position(3, 5), new Position(1, 6), new Position(8, 4),
            new Position(10, 4), new Position(9, 5), new Position(8, 6),
            new Position(10, 6)
    );

    private final int y;
    private final int x;

    public Position(int y, int x) {
        validate(y, x);
        this.y = y;
        this.x = x;
    }

    public static Position valueOf(final int y, final int x) {
        return POSITIONS.get((y - Y_MIN_THRESHOLD) * X_MAX_THRESHOLD + x - X_MIN_THRESHOLD);
    }

    public static boolean isInGungSung(Position position) {
        return GUNGSUNG_POSITION.contains(position);
    }

    public static boolean isMovingInOnlyGungSung(Position currentPosition, Position arrivalPosition) {
        return isInGungSung(currentPosition) && isInGungSung(arrivalPosition);
    }

    public static boolean isAbleToDiagonalMoveInGungSung(Position position) {
        return CAN_DIAGONAL_POSITION.contains(position);
    }

    private static List<Position> initialize() {
        List<Position> positions = new ArrayList<>();
        for (int y = Y_MIN_THRESHOLD; y <= Y_MAX_THRESHOLD; y++) {
            for (int x = X_MIN_THRESHOLD; x <= X_MAX_THRESHOLD; x++) {
                positions.add(new Position(y, x));
            }
        }
        return positions;
    }

    private static List<Position> initializeGungSung() {
        List<Position> positions = new ArrayList<>();
        for (List<Integer> yValues : Y_POINTS_IN_GUNGSUNG_EACH_TEAM) {
            for (int y : yValues) {
                for (int x : X_POINTS_IN_GUNGSUNG) {
                    positions.add(new Position(y, x));
                }
            }
        }
        return positions;
    }

    private static void validate(int y, int x) {
        if (y < Y_MIN_THRESHOLD || y > Y_MAX_THRESHOLD || x < X_MIN_THRESHOLD || x > X_MAX_THRESHOLD) {
            throw new IllegalArgumentException("[ERROR] y좌표는 1 이상 10이하, x좌표는 1이상 9이하여야 합니다.");
        }
    }

    public int calculateDifferenceForX(Position position) {
        return this.x - position.x;
    }

    public int calculateDifferenceForY(Position position) {
        return this.y - position.y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return y == position.y && x == position.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
