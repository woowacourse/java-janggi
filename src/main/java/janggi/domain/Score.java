package janggi.domain;

public record Score(int value) {

    public Score add(Score score) {
        return new Score(this.value + score.value);
    }
}
