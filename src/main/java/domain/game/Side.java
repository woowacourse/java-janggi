package domain.game;

import domain.movement.Vector;

public enum Side {
    HAN(1, Vector.down()) {
        @Override
        public Side nextTurn() {
            return CHO;
        }
    },
    CHO(10, Vector.up()) {
        @Override
        public Side nextTurn() {
            return HAN;
        }
    },
    ;

    private final int baseRow;
    private final Vector forward;

    Side(
            int baseRow,
            Vector forward
    ) {
        this.baseRow = baseRow;
        this.forward = forward;
    }

    public int calculateRowFromBase(int farFromBaseRow) {
        return baseRow + (forward.rowDelta() * farFromBaseRow);
    }

    public Vector toForward() {
        return forward;
    }

    public abstract Side nextTurn();
}
