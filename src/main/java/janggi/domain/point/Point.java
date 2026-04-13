package janggi.domain.point;

public record Point(int x, int y) {
    public Point add(int x, int y) {
        return new Point(x + this.x, y + this.y);
    }

    public Point minus(Point other) {
        return new Point(this.x - other.x, this.y - other.y);
    }

    @Override
    public String toString() {
        return "Point[" +
                "x=" + x + ", " +
                "y=" + y + ']';
    }

}
