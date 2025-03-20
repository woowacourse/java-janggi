package domain;

public abstract class AbstractPiece implements Piece {

    protected final Team team;
    protected final Score score;

    public AbstractPiece(final Team team, final Score score) {
        this.team = team;
        this.score = score;
    }

    public boolean isGreenTeam() {
        return Team.GREEN == team;
    }
}
