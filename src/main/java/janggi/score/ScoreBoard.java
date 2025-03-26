package janggi.score;

import janggi.piece.Team;

public final class ScoreBoard {

    private Score choScore;
    private Score hanScore;

    public ScoreBoard() {
        this.choScore = new Score(0);
        this.hanScore = new Score(1.5);
    }

    public void add(final Team team, final Score score) {
        if (team == Team.CHO) {
            choScore = choScore.add(score);
        }
        if (team == Team.HAN) {
            hanScore = hanScore.add(score);
        }
    }

    public double getScore(final Team team) {
        if (team == Team.CHO) {
            return choScore.getScore();
        }
        return hanScore.getScore();
    }

    public Team getWinner() {
        if (choScore.isBiggerThan(hanScore)) {
            return Team.CHO;
        }
        return Team.HAN;
    }
}
