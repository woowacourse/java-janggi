package janggi.position;

public record Direction(int x, int y) {
    public static final Direction UP = new Direction(0, -1);
    public static final Direction DOWN = new Direction(0, 1);
    public static final Direction LEFT = new Direction(1, 0);
    public static final Direction RIGHT = new Direction(-1, 0);
}
