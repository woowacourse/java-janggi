package janggi.domain.game;

import janggi.domain.piece.Side;

public record Score(double value) {

    public static Score initRedSideScore() {
        return new Score(-98.5);
    }

    public static Score initBlueSideScore() {
        return new Score(-100);
    }

    public static Score initBySide(final Side side) {
        if (side == Side.RED) {
            return initRedSideScore();
        }
        return initBlueSideScore();
    }

    public static Score zero() {
        return new Score(0);
    }

    public Score plus(Score other) {
        return new Score(value + other.value);
    }

    public Score minus(final Score other) {
        return new Score(this.value - other.value);
    }

    public boolean isGreaterThan(final Score other) {
        return this.value > other.value;
    }

    public boolean isGreaterThanZero() {
        return this.value > 0;
    }
}
