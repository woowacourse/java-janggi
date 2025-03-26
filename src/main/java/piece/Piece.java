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

    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }

    public final void move(final Position destination, final Board board) {
        if (!getMovablePositions(board).contains(destination)) {
            throw new IllegalArgumentException("해당 위치로 기물이 이동할 수 없습니다.");
        }
        if (!board.isSameTeamPosition(team, destination)) {
            board.remove(destination);
        }
        this.position = destination;
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
        return Objects.equals(position, piece.position) && getTeam() == piece.getTeam();
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, getTeam());
    }

    @Override
    public String toString() {
        return "Piece{" +
                "position=" + position +
                ", team=" + team +
                '}';
    }

}
