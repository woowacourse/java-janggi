package domain.game;

import domain.movement.Vector;
import java.util.List;

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

    public List<Vector> toForwardDiagonals() {
        return List.of(
                forward.turnLeft45Degrees(),
                forward.turnRight45Degrees()
        );
    }

    public abstract Side nextTurn();
}
