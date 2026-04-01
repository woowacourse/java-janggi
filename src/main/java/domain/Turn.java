package domain;

public class Turn {

    private Team team;

    private Turn() {
        team = Team.CHU;
    }

    public static Turn of() {
        return new Turn();
    }

    public Team change() {
        if (this.team == Team.HAN) {
            this.team = Team.CHU;
        }
        if (this.team == Team.CHU) {
            this.team = Team.HAN;
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
