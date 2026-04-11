package janggi.domain;

public class ScoreResult {

    private final Score hanScore;
    private final Score choScore;

    public ScoreResult(Score hanScore, Score choScore) {
        this.hanScore = hanScore;
        this.choScore = choScore;
    }

    public Score hanScore() {
        return hanScore;
    }

    public Score choScore() {
        return choScore;
    }

    public boolean isHanWin() {
        return hanScore.isHigherThan(choScore);
    }

    public boolean isChoWin() {
        return choScore.isHigherThan(hanScore);
    }

    public boolean isDraw() {
        return !isHanWin() && !isChoWin();
    }
}
