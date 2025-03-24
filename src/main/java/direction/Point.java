package direction;

public record Point(int column, int row) {

    public Point minus(Point other) {
        return new Point(column - other.column, row - other.row);
    }

    public Point plus(Point other) {
        return new Point(column + other.column, row + other.row);
    }

    public Point multiply(int dir) {
        return new Point(column * dir, row * dir);
    }

    public boolean isDifferentColumn(Point point) {
        return point.column != this.column;
    }

    public boolean isDifferentRow(Point point) {
        return point.row != this.row;
    }

}
