package janggi.domain;

import janggi.domain.piece.Pieces;

public class Player {

    private final String name;
    private final Team team;
    private Score score;

    public Player(final String name, final Team team, final double scoreValue) {
        this.name = name;
        this.team = team;
        this.score = new Score(scoreValue);
    }

    public Player(final String name, final Team team) {
        this.name = name;
        this.team = team;
        this.score = Pieces.getInitScore(team);
    }

    public void subtractScore(Score score) {
        this.score = this.score.subtract(score);
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public Score getScore() {
        return score;
    }
}
