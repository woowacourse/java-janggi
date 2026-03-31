package janggi.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Position {
    private static final int ROW_SIZE = 10;
    private static final int COLUMN_SIZE = 9;

    private static final List<Position> ALL_POSITION;

    static {
        ALL_POSITION = IntStream.range(0, ROW_SIZE)
                .boxed()
                .flatMap(row -> IntStream.range(0, COLUMN_SIZE)
                        .mapToObj(column -> new Position(row, column)))
                .toList();
    }

    private final int row;
    private final int column;

    private Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(int row, int column) {
        validateRange(row, column);
        return ALL_POSITION.get(calculateIndex(row, column));
    }

    private static Optional<Position> findPosition(int row, int column) {
        if (isOutOfBounds(row, column)) {
            return Optional.empty();
        }
        return Optional.of(ALL_POSITION.get(calculateIndex(row, column)));
    }

    public Optional<Position> move(int deltaRow, int deltaColumn) {
        return findPosition(this.row + deltaRow, this.column + deltaColumn);
    }

    private static void validateRange(int row, int column) {
        if (isOutOfBounds(row, column)) {
            throw new IllegalArgumentException("잘못된 좌표입니다.");
        }
    }

    private static boolean isOutOfBounds(int row, int column) {
        return row < 0 || row >= ROW_SIZE || column < 0 || column >= COLUMN_SIZE;
    }

    private static int calculateIndex(int row, int column) {
        return (row * COLUMN_SIZE) + column;
    }
}
