package janggi.domain;

import java.util.Objects;

public class Delta {

    private final int dx;
    private final int dy;

    private Delta(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Delta zero() {
        return new Delta(0, 0);
    }

    public static Delta up() {
        return new Delta(0, 1);
    }

    public static Delta down() {
        return new Delta(0, -1);
    }

    public static Delta right() {
        return new Delta(1, 0);
    }

    public static Delta left() {
        return new Delta(-1, 0);
    }

    public static Delta rightUp() {
        return new Delta(1, 1);
    }

    public static Delta rightDown() {
        return new Delta(1, -1);
    }

    public static Delta leftUp() {
        return new Delta(-1, 1);
    }

    public static Delta leftDown() {
        return new Delta(-1, -1);
    }

    public static Delta of(int dx, int dy) {
        return new Delta(dx, dy);
    }

    public static Delta scaleDown(int dx, int dy) {
        return new Delta(Integer.signum(dx), Integer.signum(dy));
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Delta add(Delta delta) {
        return new Delta(this.dx + delta.dx, this.dy + delta.dy);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Delta delta = (Delta) o;
        return dx == delta.dx && dy == delta.dy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dx, dy);
    }
}
