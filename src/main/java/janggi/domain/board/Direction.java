package janggi.domain.board;

import java.util.EnumSet;

public enum Direction {
    NE(-1, 1, EnumSet.noneOf(Direction.class)),
    NW(-1, -1, EnumSet.noneOf(Direction.class)),
    SE(1, 1, EnumSet.noneOf(Direction.class)),
    SW(1, -1, EnumSet.noneOf(Direction.class)),
    E(0, 1, EnumSet.of(NE, SE)),
    W(0, -1, EnumSet.of(NW, SW)),
    N(-1, 0, EnumSet.of(NE, NW)),
    S(1, 0, EnumSet.of(SE, SW)),
    ;

    private final int row;
    private final int col;
    private final EnumSet<Direction> directions;

    Direction(int row, int col, EnumSet<Direction> directions) {
        this.row = row;
        this.col = col;
        this.directions = directions;
    }

    public static EnumSet<Direction> cardinalDirections() {
        return EnumSet.of(N, S, E, W);
    }

    public boolean canMove(Position currentPosition) {
        return currentPosition.canMove(this.row, this.col);
    }

    public Position move(Position currentPosition) {
        return currentPosition.move(this.row, this.col);
    }

    public EnumSet<Direction> nextDiagonalDirections() {
        return directions;
    }
}
