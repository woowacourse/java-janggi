package domain;

import domain.piece.Score;

public class Player {

    private final Team team;
    private Score score;

    public Player(Team team, Score score) {
        this.team = team;
        this.score = score;
    }

    public boolean isHanTeam() {
        return team == Team.HAN;
    }

    public void increaseScore(final Score score) {
        this.score = this.score.sum(score);
    }

    public int compareScoreTo(final Player other) {
        return this.score.compareTo(other.score);
    }

    public Team getTeam() {
        return team;
    }

    public Score getScore() {
        return score;
    }
}
