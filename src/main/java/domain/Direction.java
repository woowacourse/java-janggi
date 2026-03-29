package domain;

public enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);


    private final Offset offset;

    Direction(int dx, int dy) {
        this.offset = new Offset(dx, dy);
    }

    public Offset getOffset() {
        return offset;
    }
}
