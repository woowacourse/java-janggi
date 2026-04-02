package domain.position;

import java.util.Objects;

public final class Position {
    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;
    private static final int MIN_ROW = 1;
    private static final int MIN_COLUMN = 1;

    private final int row;
    private final int column;

    private Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(int row, int column) {
        validateRange(row, column);
        return new Position(row, column);
    }

    public static Position rotate180from(Position position) {
        return Position.of(
                MAX_ROW + MIN_ROW - position.getRow(),
                MAX_COLUMN + MIN_COLUMN - position.getColumn()
        );
    }

    public PositionDelta minus(Position position) {
        return new PositionDelta(this.getRow() - position.getRow(), this.getColumn() - position.getColumn());
    }

    public Position append(int dRow, int dColumn) {
        return Position.of(this.row + dRow, this.column + dColumn);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position position)) return false;
        return Objects.equals(row, position.getRow()) &&
                Objects.equals(column, position.getColumn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    private static void validateRange(int row, int column) {
        if (row < MIN_ROW || row > MAX_ROW || column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new IllegalArgumentException("장기판 범위를 벗어났습니다.");
        }
    }
}
