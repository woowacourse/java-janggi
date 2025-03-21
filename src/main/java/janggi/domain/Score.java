package janggi.domain;

public record Score(int value) {

    public Score add(int value) {
        return new Score(this.value + value);
    }
}
