package janggi.turn;

import janggi.piece.Team;

public class Turn {

    private final Team team;
    private boolean hanWantExit;
    private boolean choWantExit;

    public Turn(final Team team, final boolean hanWantExit, final boolean choWantExit) {
        this.team = team;
        this.hanWantExit = hanWantExit;
        this.choWantExit = choWantExit;
    }

    public static Turn initialize() {
        return new Turn(Team.CHO, false, false);
    }

    public Turn moveNextTurn() {
        if (team == Team.HAN) {
            return new Turn(Team.CHO, hanWantExit, choWantExit);
        }
        return new Turn(Team.HAN, hanWantExit, choWantExit);
    }

    public void wantExit() {
        if (team == Team.HAN) {
            hanWantExit = true;
            return;
        }
        choWantExit = true;
    }

    public boolean canExit() {
        return hanWantExit && choWantExit;
    }

    public Team getTeam() {
        return team;
    }
}
