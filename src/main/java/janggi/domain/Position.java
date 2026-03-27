package janggi.domain;

import java.util.List;
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
        return ALL_POSITION.stream()
                .filter(p -> p.isSameLocation(row, column))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 좌표입니다."));
    }

    public Position move(int row, int column){
        return Position.of(this.row + row, this.column + column);
    }

    private boolean isSameLocation(int row, int column) {
        return this.row == row && this.column == column;
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }
}
