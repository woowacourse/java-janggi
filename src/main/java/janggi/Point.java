package janggi;

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
}
