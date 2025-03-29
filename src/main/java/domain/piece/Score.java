package domain.piece;

public record Score(
        int value
) {

    public Score sum(final Score other) {
        return new Score(this.value + other.value);
    }
}
