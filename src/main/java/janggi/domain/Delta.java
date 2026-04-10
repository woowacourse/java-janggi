package janggi.domain;

import java.util.List;
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

    public static Delta scaleDown(Delta delta) {
        return new Delta(Integer.signum(delta.dx), Integer.signum(delta.dy));
    }

    public static List<Delta> diagonalPaths() {
        return List.of(
                Delta.rightUp(),
                Delta.rightDown(),
                Delta.leftUp(),
                Delta.leftDown()
        );
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public boolean isGoDown() {
        return dy < 0;
    }

    public boolean isGoUp() {
        return dy > 0;
    }

    public Delta add(Delta delta) {
        return new Delta(this.dx + delta.dx, this.dy + delta.dy);
    }

    public boolean isDiagonalOverOneStep() {
        return Math.abs(dx) > 1 || Math.abs(dy) > 1;
    }

    public boolean isDiagonalOverTwoSteps() {
        return Math.abs(dx) > 2 || Math.abs(dy) > 2;
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
