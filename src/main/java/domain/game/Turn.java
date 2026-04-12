package domain.game;

import domain.board.Team;

public class Turn {

    private Team team;

    private Turn(final Team team) {
        this.team = team;
    }

    public static Turn of(final Team team) {
        return new Turn(team);
    }

    public Team change() {
        this.team = this.team.change();
        return this.team;
    }

    public String getTeamName() {
        return team.getName();
    }

    public Team getTeam() {
        return team;
    }
}
