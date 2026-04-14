package janggi.domain;

import janggi.domain.piece.strategy.Direction;

public enum Camp {
    CHO(Direction.north(), 0),
    HAN(Direction.south(), 9);

    private final Direction forward;
    private final int baselineRow;

    Camp(Direction forward, int baselineRow) {
        this.forward = forward;
        this.baselineRow = baselineRow;
    }

    public boolean isSameCamp(Camp camp) {
        return this == camp;
    }

    public boolean isCho() {
        return this == CHO;
    }

    public Direction forward() {
        return forward;
    }

    public int baselineRow() {
        return baselineRow;
    }

    public int calculateRow(int offset) {
        return baselineRow + (forward.directionRow() * offset);
    }
}
