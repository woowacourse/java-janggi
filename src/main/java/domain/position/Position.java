package domain.position;

import domain.board.Direction;

import java.util.Objects;

public final class Position {
    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;
    private static final int MIN_ROW = 1;
    private static final int MIN_COLUMN = 1;

    private static final  int MAX_PALACE_ROW = 3;
    private static final  int MAX_PALACE_COLUMN = 6;
    private static final  int MIN_PALACE_ROW = 1;
    private static final  int MIN_PALACE_COLUMN = 4;

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

    private static void validateRange(int row, int column) {
        if (row < MIN_ROW || row > MAX_ROW || column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new IllegalArgumentException("장기판 범위를 벗어났습니다.");
        }
    }

    public Coordinate minus(Position position) {
        return new Coordinate(this.getRow() - position.getRow(), this.getColumn() - position.getColumn());
    }

    public Position append(Direction direction) {
        return Position.of(getRow() + direction.getDeltaRow(), getColumn() + direction.getDeltaColumn());
    }

    public int getRow() {
        return coordinate.row();
    }

    public int getColumn() {
        return coordinate.column();
    }

    public boolean isInPalace() {
        return (coordinate.row() >= MIN_PALACE_ROW && coordinate.row() <= MAX_PALACE_ROW)
                && (coordinate.column() >= MIN_PALACE_COLUMN && coordinate.column() <= MAX_PALACE_COLUMN);
    }

    public boolean isPalaceEdgeCenter() {
        return (Objects.equals(coordinate, new Coordinate(2, 4)) ||
                Objects.equals(coordinate, new Coordinate(2, 6)) ||
                Objects.equals(coordinate, new Coordinate(1,5)) ||
                Objects.equals(coordinate, new Coordinate(3,5))
        );
    }

    public boolean isPalaceEdge() {
        return (Objects.equals(coordinate, new Coordinate(1, 4)) ||
                Objects.equals(coordinate, new Coordinate(1, 6)) ||
                Objects.equals(coordinate, new Coordinate(3,4)) ||
                Objects.equals(coordinate, new Coordinate(3,6))
        );
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
}
