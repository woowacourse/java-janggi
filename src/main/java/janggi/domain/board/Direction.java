package janggi.domain.board;

import java.util.Collections;
import java.util.List;

public enum Direction {
    NE(-1, 1, Collections.emptyList()),
    NW(-1, -1, Collections.emptyList()),
    SE(1, 1, Collections.emptyList()),
    SW(1, -1, Collections.emptyList()),
    E(0, 1, List.of(NE, SE)),
    W(0, -1, List.of(NW, SW)),
    S(1, 0, List.of(SE, SW)),
    N(-1, 0, List.of(NE, NW)),
    ;

    private final int row;
    private final int col;
    private final List<Direction> directions;

    Direction(int row, int col, List<Direction> directions) {
        this.row = row;
        this.col = col;
        this.directions = directions;
    }

    public boolean canMove(Position currentPosition) {
        return currentPosition.canMove(this.row, this.col);
    }

    public Position move(Position currentPosition) {
        return currentPosition.move(this.row, this.col);
    }

    public List<Direction> nextDiagonalDirections() {
        return directions;
    }
}
