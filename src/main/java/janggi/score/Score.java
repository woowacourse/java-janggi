package janggi.score;

public final class Score {

    private final double score;

    public Score(final double score) {
        this.score = score;
    }

    public Score add(final Score score) {
        return new Score(this.score + score.score);
    }

    public double getScore() {
        return score;
    }
}
