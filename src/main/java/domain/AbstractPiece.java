package domain;

import java.util.Objects;

/**
 * score는 반드시 유니크해야한다.
 */
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

    @Override
    public final Score getScore() {
        return score;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final AbstractPiece that)) {
            return false;
        }
        return getScore() == that.getScore();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getScore());
    }
}
