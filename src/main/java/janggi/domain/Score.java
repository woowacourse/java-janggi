package janggi.domain;

public record Score(double value) {
    public Score plus(Score other) {
        return new Score(this.value + other.value);
    }
}
