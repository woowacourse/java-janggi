package domain.game;

import domain.vo.Team;

public class Turn {
    private final Team team;

    public Turn(Team team) {
        this.team = team;
    }

    public String display() {
        return team.getTeamName();
    }

    public Turn changeTeam() {
        return new Turn(team.getEnemy());
    }
}
