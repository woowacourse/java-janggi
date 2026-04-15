package domain.game;

public record Score(double score) {
    public Score add(Score other) {
        return new Score(this.score + other.score());
    }

    @Override
    public String toString() {
        return String.format("%.1f", score);
    }
}

