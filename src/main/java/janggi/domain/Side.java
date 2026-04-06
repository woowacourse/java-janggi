package janggi.domain;

import janggi.domain.rule.route.Direction;

// TODO 다형성 적용 고려해보기
public enum Side {

    HAN(1.5),
    CHO(1);

    private final double score;

    Side(double score) {
        this.score = score;
    }

    public Side switchTurn() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public Direction getBackDirection() {
        if (this == HAN) {
            return Direction.BACK;
        }
        return Direction.FRONT;
    }

    public double getDefaultScore() {
        return score;
    }
}
