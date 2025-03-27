package domain.player;

public class Player {

    private final String name;
    private final Team team;
    private int score = 0;

    public Player(final String name, final Team team) {
        this.name = name;
        this.team = team;
    }

    public void addScore(final int score) {
        this.score += score;
    }

    public String name() {
        return name;
    }

    public Team team() {
        return team;
    }

    public int score() {
        return score;
    }
}
