package janggi.board.point;

public record Point(
        int x,
        int y
) {

    public boolean isHorizontal(Point otherPoint) {
        return this.y == otherPoint.y;
    }

    public boolean isVertical(Point otherPoint) {
        return this.x == otherPoint.x;
    }

    public int calculateXDistance(Point otherPoint) {
        return Math.abs(this.x - otherPoint.x);
    }

    public int calculateYDistance(Point otherPoint) {
        return Math.abs(this.y - otherPoint.y);
    }

    public boolean isBehind(Point other) {
        return this.y < other.y;
    }

    public boolean isOneStepAway(Point other) {
        return manhattanDistance(other) == 1;
    }

    private int manhattanDistance(Point other) {
        return calculateXDistance(other) + calculateYDistance(other);
    }
}
