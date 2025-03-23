package janggi.domain;

public class Offset {

    private final int dx;
    private final int dy;

    public Offset(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }

    public Position applyTo(Position base) {
        return new Position(base.getX() + dx, base.getY() + dy);
    }
}
