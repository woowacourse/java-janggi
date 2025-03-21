package janggi.domain;

public class Player {

    private final String name;
    private final Team team;
    private Score score;

    private Player(final String name, final Team team, int score) {
        this.name = name;
        this.team = team;
        this.score = new Score(score);
    }

    public Player(final String name, final Team team) {
        this(name, team, 0);
    }

    public void addScore(final Score score) {
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
