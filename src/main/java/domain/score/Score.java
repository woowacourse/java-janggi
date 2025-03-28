package domain.score;

public record Score(double score) {

    public Score plus(Score other) {
        return new Score(this.score + other.score);
    }

    public Score multiply(Score other) {
        return new Score(this.score * other.score);
    }

    public boolean greaterThan(Score other) {
        return this.score > other.score;
    }
}
