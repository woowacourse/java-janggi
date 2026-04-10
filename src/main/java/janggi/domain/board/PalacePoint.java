package janggi.domain.board;

import java.util.List;

import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;

public enum PalacePoint {
    LEFT_UP(1, -1, List.of(Direction.SOUTH_EAST)),
    RIGHT_UP(1, 1, List.of(Direction.SOUTH_WEST)),
    CENTER(0, 0, List.of(Direction.SOUTH_EAST, Direction.SOUTH_WEST, Direction.NORTH_EAST, Direction.NORTH_WEST)),
    LEFT_DOWN(-1, -1, List.of(Direction.NORTH_EAST)),
    RIGHT_DOWN(-1, 1, List.of(Direction.NORTH_WEST));

    private final int x;
    private final int y;
    private final Pattern pattern;

    PalacePoint(int x, int y, List<Direction> directions) {
        this.x = x;
        this.y = y;
        this.pattern = new Pattern(directions);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Pattern getPattern() {
        return pattern;
    }
}

