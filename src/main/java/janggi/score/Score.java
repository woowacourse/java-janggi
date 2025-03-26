package janggi.score;

public final class Score {

    private final double score;

    public Score(final double score) {
        this.score = score;
    }

    public Score add(final Score another) {
        return new Score(this.score + another.score);
    }

    public double getScore() {
        return score;
    }

    public boolean isBiggerThan(final Score another) {
        return score > another.score;
    }
}
