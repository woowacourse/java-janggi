package domain.game;

import domain.piece.Team;

public class Turn {
    private final Team team;

    public Turn(Team team) {
        this.team = team;
    }

    public Turn changeTeam() {
        return new Turn(team.enemy());
    }

    public Team getTeam() {
        return team;
    }
}
