package janggi.domain;

public record Score(int value) {

    public Score add(final Score score) {
        return new Score(this.value + score.value());
    }
}
