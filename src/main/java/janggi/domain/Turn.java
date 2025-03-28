package janggi.domain;

public class Turn {
    private final Team team;

    public Turn(Team team) {
        this.team = team;
    }

    public static Turn First() {
        return new Turn(Team.BLUE);
    }

    public Turn getNextTurn() {
        if(team == Team.BLUE) {
            return new Turn(Team.RED);
        }
        return new Turn(Team.BLUE);
    }

    public Team getTeam() {
        return team;
    }
}
