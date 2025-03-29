package domain.piece;

public record Score(
        int value
) implements Comparable<Score> {

    public Score sum(final Score other) {
        return new Score(this.value + other.value);
    }

    @Override
    public int compareTo(final Score o) {
        return Integer.compare(this.value, o.value);
    }
}
