package janggi.domain;

public class Score {

    private static final double HAN_BONUS = 1.5;

    private final double value;

    private Score(double value) {
        this.value = value;
    }

    public static Score of(double value) {
        return new Score(value);
    }

    public Score applyHanBonus() {
        return new Score(value + HAN_BONUS);
    }

    public boolean isHigherThan(Score other) {
        return this.value > other.value;
    }

    public double getValue() {
        return value;
    }
}
