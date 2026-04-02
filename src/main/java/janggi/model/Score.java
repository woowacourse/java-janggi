package janggi.model;

public record Score(int value) {
    public Score add(Score otherScore) {
        return new Score(this.value + otherScore.value);
    }

    public int getValue() {
        return this.value;
    }

    public static Score zero() {
        return new Score(0);
    }
}
