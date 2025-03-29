package domain.player;

public class Player {

    private final String name;
    private final Team team;
    private float score;

    public Player(final String name, final Team team) {
        this.name = name;
        this.team = team;
        this.score = team.getDefaultScore();
    }

    public String name() {
        return name;
    }

    public Team team() {
        return team;
    }

    public float score() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }
}
