package piece;

import java.util.Objects;
import java.util.Set;

import board.Board;
import board.Position;

public abstract class Piece {

    protected final Team team;

    protected Piece(final Team team) {
        this.team = team;
    }

    protected abstract Set<Position> getMovablePositions(final Position position, final Board board);

    public abstract String getDisplayName();

    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }

    public final boolean canMove(final Position start, final Position destination, final Board board) {
        return getMovablePositions(start, board).contains(destination);
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Piece piece)) {
            return false;
        }
        return getTeam() == piece.getTeam();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTeam());
    }

}
