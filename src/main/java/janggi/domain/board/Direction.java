package janggi.domain.board;

import janggi.domain.game.Side;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public enum Direction {
    E(0, 1),
    W(0, -1),
    S(1, 0),
    N(-1, 0),
    NE(-1, 1),
    NW(-1, -1),
    SE(1, 1),
    SW(1, -1),
    NONE(0, 0),
    ;

    private final int row;
    private final int column;

    private static final Map<Direction, List<Direction>> ADJACENT_DIAGONALS = Map.of(
            N, List.of(NE, NW),
            S, List.of(SE, SW),
            E, List.of(NE, SE),
            W, List.of(NW, SW)
    );

    Direction(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public List<Direction> getAdjacentDiagonals() {
        return ADJACENT_DIAGONALS.getOrDefault(this, Collections.emptyList());
    }

    public int getNextRow(int currentRow) {
        return currentRow + this.row;
    }

    public int getNextColumn(int currentColumn) {
        return currentColumn + this.column;
    }

    public boolean isDiagonal() {
        return (this == NE) || (this == NW) || (this == SE) || (this == SW);
    }

    // 전진 방향
    public boolean isForwardFor(Side side) {
        if (side == Side.CHO) {
            return this.row < 0; // 초: 위 (-1)
        }
        return this.row > 0; // 한: 아래 (+1)
    }
}
