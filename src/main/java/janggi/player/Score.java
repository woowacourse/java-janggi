package janggi.player;

public record Score(int value) {

    public static Score from(final int value) {
        return new Score(value);
    }

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
        return Score.win().add(new Score(1));
    }

    public static Score win() {
        return new Score(10000);
    }

    public Score add(final Score score) {
        return new Score(this.value + score.value);
    }

    public boolean isGreaterThan(final Score other) {
        return value > other.value;
    }

    public boolean isLessThan(final Score other) {
        return value < other.value;
    }
}
