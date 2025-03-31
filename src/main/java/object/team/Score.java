package object.team;

public class Score {

    private final int score;

    public Score(int score) {
        this.score = score;
    }

    public Score add(int score) {
        return new Score(this.score + score);
    }

    public int getScore() {
        return score;
    }
}
