package janggi.domain;

public class Delta {

    private final int dx;
    private final int dy;

    private Delta(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Delta createUp() {
        return new Delta(0, 1);
    }

    public static Delta createDown() {
        return new Delta(0, -1);
    }

    public static Delta createRight() {
        return new Delta(1, 0);
    }

    public static Delta createLeft() {
        return new Delta(-1, 0);
    }

    public static Delta createLeftUp() {
        return new Delta(-1, 1);
    }

    public static Delta createRightUp() {
        return new Delta(1, 1);
    }

    public static Delta createLeftDown() {
        return new Delta(-1, -1);
    }

    public static Delta createRightDown() {
        return new Delta(1, -1);
    }

    public static Delta from(int dx, int dy) {
        if (isVertical(dx)) {
            return vertical(dy);
        }
        if (isHorizontal(dy)) {
            return horizontal(dx);
        }
        if (isDiagonal(dx, dy)) {
            return diagonal(dx, dy);
        }
        throw new IllegalArgumentException("지원하지 않는 방향입니다.");
    }

    private static boolean isVertical(int dx) {
        return dx == 0;
    }

    private static boolean isHorizontal(int dy) {
        return dy == 0;
    }

    private static boolean isDiagonal(int dx, int dy) {
        return Math.abs(dx) == 1 && Math.abs(dy) == 1;
    }

    private static Delta vertical(int dy) {
        if (dy == 1) {
            return createUp();
        }
        return createDown();
    }

    private static Delta horizontal(int dx) {
        if (dx == 1) {
            return createRight();
        }
        return createLeft();
    }

    private static Delta diagonal(int dx, int dy) {
        if (dx == -1 && dy == 1) {
            return createLeftUp();
        }
        if (dx == 1 && dy == 1) {
            return createRightUp();
        }
        if (dx == -1 && dy == -1) {
            return createLeftDown();
        }
        return createRightDown();
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }
}
