package domain.position;

import domain.board.Direction;

import java.util.Objects;

public final class Position {
    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;
    private static final int MIN_ROW = 1;
    private static final int MIN_COLUMN = 1;
    private final Coordinate coordinate;

    private Position(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public static Position of(int row, int column) {
        validateRange(row, column);
        return new Position(new Coordinate(row, column));
    }

    public static Position rotate180from(Position position) {
        return new Position(Coordinate.rotate180from(position.getRow(), position.getColumn()));
    }

    public Coordinate minus(Position position) {
        return new Coordinate(this.getRow() - position.getRow(), this.getColumn() - position.getColumn());
    }

    public Position append(Direction direction) {
        return Position.of(getRow() + direction.getdRow(), getColumn() + direction.getdColumn());
    }

    public int getRow() {
        return coordinate.row();
    }

    public int getColumn() {
        return coordinate.column();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position position)) return false;
        return Objects.equals(coordinate.row(), position.getRow()) &&
                Objects.equals(coordinate.column(), position.getColumn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate.row(), coordinate.column());
    }

    private static void validateRange(int row, int column) {
        if (row < MIN_ROW || row > MAX_ROW || column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new IllegalArgumentException("장기판 범위를 벗어났습니다.");
        }
    }
}
