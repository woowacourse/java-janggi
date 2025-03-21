package domain;

public class Turn {

    private Team team;

    public Turn(final Team team) {
        this.team = team;
    }

    public Team currentTeam() {
        return team;
    }

    public void change() {
        if (team == Team.GREEN) {
            team = Team.RED;
            return;
        }

        team = Team.GREEN;
    }
}
