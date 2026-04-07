package domain.piece;

import domain.Direction;

public enum Team {
    CHO(Direction.DOWN, 0), HAN(Direction.UP, 1.5);

    private final Direction backwardDirection;
    private final double score;

    Team(Direction backwardDirection, double score) {
        this.backwardDirection = backwardDirection;
        this.score = score;
    }

    public Direction getBackwardDirection() {
        return backwardDirection;
    }

    public double getScore() {
        return score;
    }

    public Team opposite() {
        if (this == Team.CHO) {
            return Team.HAN;
        }
        return Team.CHO;
    }
}
