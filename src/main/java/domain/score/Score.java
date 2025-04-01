package domain.score;

import domain.type.ChessTeam;

public record Score(double value) implements Comparable<Score> {

    public static Score initScore(final ChessTeam team) {
        if (team == ChessTeam.BLUE) {
            return zero();
        }
        return new Score(1.5);
    }

    private static Score zero() {
        return new Score(0);
    }

    public static Score hundred() {
        return new Score(100.0);
    }

    public Score add(Score other) {
        return new Score(value + other.value);
    }

    public Score subtract(Score other) { return new Score(value - other.value); }

    public boolean isPositiveScoreValue() {
        return value > 0;
    }
    @Override
    public int compareTo(final Score o) {
        return Double.compare(this.value, o.value);
    }
}
