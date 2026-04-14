package janggi.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class JanggiPosition {
    private static final int ROW_SIZE = 10;
    private static final int COLUMN_SIZE = 9;
    private static final int PALACE_CENTER_INDEX = 4;

    private static final List<JanggiPosition> ALL_POSITION;
    private static final List<JanggiPosition> CHO_PALACE;
    private static final List<JanggiPosition> HAN_PALACE;

    static {
        ALL_POSITION = IntStream.range(0, ROW_SIZE)
                .boxed()
                .flatMap(row -> IntStream.range(0, COLUMN_SIZE)
                        .mapToObj(column -> new JanggiPosition(row, column)))
                .toList();
    }

    static {
        CHO_PALACE = IntStream.range(0, 3)
                .boxed()
                .flatMap(row -> IntStream.range(3, 6)
                        .mapToObj(column -> JanggiPosition.of(row, column)))
                .toList();
        HAN_PALACE = IntStream.range(7, 10)
                .boxed()
                .flatMap(row -> IntStream.range(3, 6)
                        .mapToObj(column -> JanggiPosition.of(row, column)))
                .toList();
    }

    private final int row;
    private final int column;

    private JanggiPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static JanggiPosition of(int row, int column) {
        validateRange(row, column);
        return ALL_POSITION.get(calculateIndex(row, column));
    }

    private static Optional<JanggiPosition> findPosition(int row, int column) {
        if (isOutOfBounds(row, column)) {
            return Optional.empty();
        }
        return Optional.of(ALL_POSITION.get(calculateIndex(row, column)));
    }

    public Optional<JanggiPosition> move(int deltaRow, int deltaColumn) {
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

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isPalaceDiagonal() {
        if (isChoPalace()) {
            return isDiagonal(CHO_PALACE);
        }
        if (isHanPalace()) {
            return isDiagonal(HAN_PALACE);
        }
        return false;
    }

    public boolean isPalace() {
        return isChoPalace() || isHanPalace();
    }

    private boolean isChoPalace() {
        return CHO_PALACE.contains(this);
    }

    private boolean isHanPalace() {
        return HAN_PALACE.contains(this);
    }

    private boolean isDiagonal(List<JanggiPosition> palace) {
        JanggiPosition palaceCenter = palace.get(PALACE_CENTER_INDEX);
        return (this.row == palaceCenter.row) == (this.column == palaceCenter.column);
    }
}
