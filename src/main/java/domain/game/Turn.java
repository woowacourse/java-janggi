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
        if (this.team == Team.HAN) {
            this.team = Team.CHU;
            return this.team;
        }
        if (this.team == Team.CHU) {
            this.team = Team.HAN;
            return this.team;
        }
        return this.team;
    }

    public String getTeamName() {
        return team.getName();
    }

    public Team getTeam() {
        return team;
    }
}
