package domain.game;

import domain.movement.Vector;
import java.util.List;

public enum Side {
    HAN(1, 1.5, Vector.down()) {
        @Override
        public Side nextTurn() {
            return CHO;
        }
    },
    CHO(10, 0, Vector.up()) {
        @Override
        public Side nextTurn() {
            return HAN;
        }
    },
    ;

    private final int baseRow;
    private final double extraScore;
    private final Vector forward;

    Side(
            int baseRow,
            double extraScore,
            Vector forward
    ) {
        this.baseRow = baseRow;
        this.extraScore = extraScore;
        this.forward = forward;
    }

    public int calculateRowFromBase(int farFromBaseRow) {
        return baseRow + (forward.rowDelta() * farFromBaseRow);
    }

    public double calculateTotalScore(double score) {
        return score + extraScore;
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
