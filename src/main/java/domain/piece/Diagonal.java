package domain.piece;


import static domain.piece.Direction.*;

import java.util.List;

public enum Diagonal {
    UP_RIGHT(List.of(UP, RIGHT), -1, 1),
    UP_LEFT(List.of(UP, LEFT),-1, -1),
    DOWN_RIGHT(List.of(DOWN, RIGHT),1, 1),
    DOWN_LEFT(List.of(DOWN, RIGHT),1, -1);

    private final List<Direction> possibleDirections;
    private final int x;
    private final int y;

    Diagonal(List<Direction> possibleDirections, int x, int y) {
        this.possibleDirections = possibleDirections;
        this.x = x;
        this.y = y;
    }

    public List<Direction> getPossibleDirections() {
        return possibleDirections;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean notContains(Direction direction) {
        return !possibleDirections.contains(direction);
    }
}
