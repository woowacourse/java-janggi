package janggi.piece;

public class Score {

    private final double score;

    public Score(final double score) {
        this.score = score;
    }

    public double sum(final double totalScore) {
        return this.score + totalScore;
    }
}
