package janggi.model;

public record Score(double value) {
    public Score add(Score otherScore) {
        return new Score(this.value + otherScore.value);
    }

    public static Score zero() {
        return new Score(0);
    }
}
