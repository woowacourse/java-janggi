package domain;

public class Player {
    private final Team team;

    public Player(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isFirstAttack() {
        return team.isFirst();
    }
}
