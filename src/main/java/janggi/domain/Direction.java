package janggi.domain;

public enum Direction {
    E(0, 1),
    W(0, -1),
    S(1, 0),
    N(-1, 0),
    NE(-1, 1),
    NW(-1, -1),
    SE(1, 1),
    SW(1, -1),
    ;

    public final int row;
    public final int col;

    Direction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position move(Position currentPosition) {
        return currentPosition.move(this.row, this.col);
    }
}
