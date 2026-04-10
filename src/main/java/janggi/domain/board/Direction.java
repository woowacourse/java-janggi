package janggi.domain.board;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public enum Direction {
    NE(-1, 1),
    NW(-1, -1),
    SE(1, 1),
    SW(1, -1),
    E(0, 1),
    W(0, -1),
    N(-1, 0),
    S(1, 0),
    ;

    private static final Map<Direction, EnumSet<Direction>> NEXT_DIAGONALS = new EnumMap<>(Direction.class);

    static {
        NEXT_DIAGONALS.put(N, EnumSet.of(NE, NW));
        NEXT_DIAGONALS.put(S, EnumSet.of(SE, SW));
        NEXT_DIAGONALS.put(E, EnumSet.of(NE, SE));
        NEXT_DIAGONALS.put(W, EnumSet.of(NW, SW));
    }

    private final int row;
    private final int col;

    Direction(int row, int col) {
        this.row = row;
        this.col = col;
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
        return NEXT_DIAGONALS.getOrDefault(this, EnumSet.noneOf(Direction.class));
    }
}
