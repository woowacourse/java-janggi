package janggi.domain.board.point;

public record Point(int x, int y) {

    public Point add(int x, int y) {
        return new Point(x + this.x, y + this.y);
    }

    @Override
    public String toString() {
        return "Point[" +
                "x=" + x + ", " +
                "y=" + y + ']';
    }

}
