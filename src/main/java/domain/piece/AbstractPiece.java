package domain.piece;

import domain.Score;
import domain.Team;

public abstract class AbstractPiece implements Piece {

    protected final Team team;
    protected final Score score;

    protected AbstractPiece(final Team team, final Score score) {
        this.team = team;
        this.score = score;
    }

    public final boolean isGreenTeam() {
        return Team.GREEN == team;
    }
}
