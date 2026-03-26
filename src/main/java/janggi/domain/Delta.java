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

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }
}
