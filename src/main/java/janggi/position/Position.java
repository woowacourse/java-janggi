package janggi.position;

public record Position(int x, int y) {

    public boolean isHorizontalTo(Position otherPosition) {
        return this.y == otherPosition.y;
    }

    public boolean isVerticalTo(Position otherPosition) {
        return this.x == otherPosition.x;
    }

    public int calculateXDistanceTo(Position otherPosition) {
        return Math.abs(this.x - otherPosition.x);
    }

    public int calculateYDistance(Position otherPosition) {
        return Math.abs(this.y - otherPosition.y);
    }

}
