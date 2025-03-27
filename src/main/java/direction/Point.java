package direction;

public record Point(int column, int row) {

    public Point minus(Point other) {
        return new Point(column - other.column, row - other.row);
    }

    public Point plus(Point other) {
        return new Point(column + other.column, row + other.row);
    }

    public boolean isDifferentColumn(Point point) {
        return point.column != this.column;
    }

    public boolean isDifferentRow(Point point) {
        return point.row != this.row;
    }

    public Point move(Movement movement) {
        return new Point(column + movement.getColumn(), row + movement.getRow());
    }

    public int moveCount(Point point) {
        return Math.abs(point.row) + Math.abs(point.column);
    }
}
