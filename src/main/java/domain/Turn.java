package domain;

import domain.piece.Team;

public class Turn {

    private static final Team START_TEAM = Team.CHO;

    private Team team;

    public Turn() {
        this.team = START_TEAM;
    }

    public void changeTurn() {
        this.team = team.inverse();
    }

    public Team team() {
        return team;
    }
}
