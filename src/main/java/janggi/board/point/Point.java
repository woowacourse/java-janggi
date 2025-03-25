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

    public Point getNextHorizontalStep(Point other) {
        if (this.x < other.x) {
            return new Point(this.x + 1, this.y);
        }
        return new Point(this.x - 1, this.y);
    }

    public Point getNextVerticalStep(Point other) {
        if (this.y < other.y) {
            return new Point(this.x, this.y + 1);
        }
        return new Point(this.x, this.y - 1);
    }

    public Point middlePoint(Point other) {
        return new Point((this.x + other.x) / 2, (this.y + other.y) / 2);
    }
}
