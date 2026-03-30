package janggi.domain.board;

import java.util.Collections;
import java.util.List;

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

    private final int row;
    private final int col;

    Direction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position move(Position currentPosition) {
        return currentPosition.move(this.row, this.col);
    }

    public List<Direction> getAdjacentDiagonals() {
        if (this == N) return List.of(NE, NW);
        if (this == S) return List.of(SE, SW);
        if (this == E) return List.of(NE, SE);
        if (this == W) return List.of(NW, SW);
        return Collections.emptyList();
    }
}
