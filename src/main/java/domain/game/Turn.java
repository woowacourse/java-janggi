package domain.game;

import domain.piece.Team;

public class Turn {

    private Team team;

    public Turn(Team team) {
        this.team = team;
    }

    public void opposite() {
        if (this.team == Team.CHO) {
            this.team = Team.HAN;
            return;
        }
        this.team = Team.CHO;
    }

    public Team getTeam() {
        return team;
    }

    public static Turn getStartingTurn() {
        return new Turn(Team.CHO);
    }
}
