package game;

import domain.type.ChessTeam;

public class Turn {

    private final ChessTeam team;

    public Turn(final ChessTeam team) {
        this.team = team;
    }

    public static Turn create() {
        return new Turn(ChessTeam.BLUE);
    }

    public Turn change() {
        if (this.team == ChessTeam.BLUE) {
            return new Turn(ChessTeam.RED);
        }
        return new Turn(ChessTeam.BLUE);
    }

    public ChessTeam getTeam() {
        return team;
    }

    public boolean equalsTeam(final ChessTeam team) {
        return this.team == team;
    }
}
