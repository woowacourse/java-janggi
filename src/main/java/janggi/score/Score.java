package janggi.score;

public record Score(double score) {

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
