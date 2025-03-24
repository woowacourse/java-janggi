package piece;

import board.Board;
import board.Position;

import java.util.Objects;
import java.util.Set;

public abstract class Piece {

    protected Position position;
    protected final Team team;

    protected Piece(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    protected abstract Set<Position> getMovablePositions(final Board board);

    public abstract String getDisplayName();

    public boolean isSamePosition(final Position position) {
        return this.position.equals(position);
    }

    public boolean isSameTeam(final Piece otherPiece) {
        return this.team == otherPiece.team;
    }

    public final void move(final Position position, final Board board) {
        if (!getMovablePositions(board).contains(position)) {
            throw new IllegalArgumentException();
        }
        if (!board.isSameTeam(this, position)) {
            board.remove(position);
        }
        this.position = position;
    }

    public Position getPosition() {
        return position;
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
        return Objects.equals(getPosition(), piece.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPosition());
    }

    @Override
    public String toString() {
        return "Piece{" +
                "position=" + position +
                ", team=" + team +
                '}';
    }

}
