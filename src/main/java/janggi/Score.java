package janggi;

public record Score(double value) {

    public static Score initRedSideScore() {
        return new Score(73.5);
    }

    public static Score initBlueSideScore() {
        return new Score(72);
    }

    public static Score zero() {
        return new Score(0);
    }

    public Score minusScore(final Score other) {
        return new Score(this.value - other.value);
    }

    public boolean isEnd() {
        return this.value <= 0;
    }

    public double getValue() {
        return value;
    }
}
