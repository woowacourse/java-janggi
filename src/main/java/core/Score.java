package core;

public record Score(double value) {

    public static Score zero() {
        return new Score(0);
    }

    public Score add(Score other) {
        return new Score(value + other.value);
    }

    public Score addHandicap() {
        return add(new Score(1.5));
    }

    public boolean isGreaterThan(Score other) {
        return value > other.value;
    }
}
