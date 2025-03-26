package janggi.domain;

public class Player {

    private final String name;
    private final Team team;
    private Score score;

    public Player(final String name, final Team team, final int scoreValue) {
        this.name = name;
        this.team = team;
        this.score = new Score(scoreValue);
    }

    public Player(final String name, final Team team) {
        this.name = name;
        this.team = team;
        this.score = new Score(0);
    }

    public void addScore(Score score) {
        this.score = this.score.add(score);
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
