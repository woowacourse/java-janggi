package domain;

public final class Score {
    private int choScore;
    private int hanScore;

    public Score(int choScore, int hanScore) {
        this.choScore = choScore;
        this.hanScore = hanScore;
    }

    public int getChoScore() {
        return choScore;
    }

    public int getHanScore() {
        return hanScore;
    }
}
