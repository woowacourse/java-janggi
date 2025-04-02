package janggi.domain.piece;

public enum Point {

    THIRTEEN(13),
    SEVEN(7),
    FIVE(5),
    THREE(3),
    TWO(2),
    NONE(0);

    private final int point;

    Point(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }
}
