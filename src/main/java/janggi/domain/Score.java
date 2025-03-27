package janggi.domain;

public record Score(double value) {

    public Score subtract(Score score) {
        return new Score(this.value - score.value);
    }
}
