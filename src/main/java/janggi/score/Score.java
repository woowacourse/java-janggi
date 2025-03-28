package janggi.score;

public record Score(double value) {

    public Score multiply(int count) {
        return new Score(value * count);
    }

    public Score plus(double scoreValue) {
        return new Score(value + scoreValue);
    }

    public Score plus(Score score) {
        return new Score(this.value + score.value);
    }

    public Score minus(Score score) {
        return new Score(this.value - score.value);
    }
}
