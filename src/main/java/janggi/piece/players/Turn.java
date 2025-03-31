package janggi.piece.players;

import java.util.Objects;

public class Turn {

    private final Team team;
    private boolean hanWantExit;
    private boolean choWantExit;

    public Turn(final Team team, final boolean hanWantExit, final boolean choWantExit) {
        this.team = team;
        this.hanWantExit = hanWantExit;
        this.choWantExit = choWantExit;
    }

    public static Turn initialize(final Team team) {
        return new Turn(team, false, false);
    }

    public Turn moveNextTurn() {
        if (team == Team.HAN) {
            return new Turn(Team.CHO, hanWantExit, choWantExit);
        }
        return new Turn(Team.HAN, hanWantExit, choWantExit);
    }

    public Turn wantExit() {
        if (team == Team.HAN) {
            hanWantExit = true;
            return new Turn(team.getOppositeTeam(), hanWantExit, choWantExit);
        }
        choWantExit = true;
        return new Turn(team.getOppositeTeam(), hanWantExit, choWantExit);
    }

    public boolean canExit() {
        return hanWantExit && choWantExit;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Turn turn)) {
            return false;
        }
        return hanWantExit == turn.hanWantExit && choWantExit == turn.choWantExit && getTeam() == turn.getTeam();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeam(), hanWantExit, choWantExit);
    }

    public Team getTeam() {
        return team;
    }
}
