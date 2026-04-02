package janggi.domain.piece;

public record Score(
        int value
) {
    public static final Score NONE = new Score(0);

    public Score add(Score other) {
        return new Score(other.value + value);
    }

    public boolean isBigger(Score score) {
        return this.value > score.value;
    }
}
