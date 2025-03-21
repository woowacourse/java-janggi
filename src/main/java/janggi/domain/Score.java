package janggi.domain;

public record Score(int value) {

    public static Score Soldier() {
        return new Score(2);
    }

    public static Score Guard() {
        return new Score(3);
    }

    public static Score Elephant() {
        return new Score(3);
    }

    public static Score Horse() {
        return new Score(5);
    }

    public static Score Cannon() {
        return new Score(7);
    }

    public static Score Chariot() {
        return new Score(13);
    }

    public Score add(int value) {
        return new Score(this.value + value);
    }
}
