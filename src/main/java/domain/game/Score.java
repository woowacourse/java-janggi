package domain.game;

public record Score(double value) {
    public Score add(Score other) {
        return new Score(value + other.value());
    }
}
