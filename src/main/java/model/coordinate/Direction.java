package model.coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public enum Direction {
    EAST(0, 1),
    WEST(0, -1),
    NORTH(-1, 0),
    SOUTH(1, 0),
    NORTH_EAST(-1, 1),
    SOUTH_EAST(1, 1),
    NORTH_WEST(-1, -1),
    SOUTH_WEST(1, -1);

    private final int rowDelta;
    private final int colDelta;

    Direction(int rowDelta, int colDelta) {
        this.rowDelta = rowDelta;
        this.colDelta = colDelta;
    }

    public static Direction from(Position start, Position end) {
        Displacement displacement = end.minus(start);
        return Stream.of(values())
                .filter(direction -> hasSameDirection(direction, displacement))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("직선 방향이 아닙니다."));
    }

    private static boolean hasSameDirection(Direction direction, Displacement displacement) {
        return direction.rowDelta * displacement.col() == direction.colDelta * displacement.row()
                && direction.rowDelta * displacement.row() >= 0
                && direction.colDelta * displacement.col() >= 0;
    }

    public static List<Direction> decomposePieceRoute(Position start, Position end) {
        Displacement displacement = end.minus(start);
        Direction cardinal = resolveCardinal(displacement);
        Direction diagonal = resolveDiagonal(displacement);
        int diagonalCount = Math.min(Math.abs(displacement.row()), Math.abs(displacement.col()));

        List<Direction> directions = new ArrayList<>();
        directions.add(cardinal);
        for (int i = 0; i < diagonalCount; i++) {
            directions.add(diagonal);
        }
        return directions;
    }

    private static Direction resolveCardinal(Displacement displacement) {
        if (Math.abs(displacement.row()) > Math.abs(displacement.col())) {
            return findByRowCol(Integer.signum(displacement.row()), 0);
        }
        return findByRowCol(0, Integer.signum(displacement.col()));
    }

    private static Direction resolveDiagonal(Displacement displacement) {
        return findByRowCol(Integer.signum(displacement.row()), Integer.signum(displacement.col()));
    }

    private static Direction findByRowCol(int row, int col) {
        return Stream.of(values())
                .filter(d -> d.rowDelta == row && d.colDelta == col)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 방향입니다."));
    }

    public int rowDelta() {
        return rowDelta;
    }

    public int colDelta() {
        return colDelta;
    }
}
