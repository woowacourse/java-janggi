package janggi;

public record Score(int value) {

    public static Score soldier() {
        return new Score(2);
    }

    public static Score guard() {
        return new Score(3);
    }

    public static Score elephant() {
        return new Score(3);
    }

    public static Score horse() {
        return new Score(5);
    }

    public static Score cannon() {
        return new Score(7);
    }

    public static Score chariot() {
        return new Score(13);
    }

    public static Score general() {
        return new Score(19980608);
    }

    public static Score win() {
        return new Score(10000);
    }

    public Score add(Score score) {
        return new Score(this.value + score.value);
    }

    public boolean isGreaterThan(Score other) {
        return value > other.value;
    }
}
