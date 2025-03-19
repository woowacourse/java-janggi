package domain;

import java.util.Objects;
import java.util.Set;

public abstract class Piece {

    protected Position position;
    protected final Team team;
    protected final Board board;

    protected Piece(Position position, Team team, Board board) {
        this.position = position;
        this.team = team;
        this.board = board;
    }

    protected abstract Set<Position> getMovablePositions();

    public boolean isSamePosition(final Position position) {
        return this.position.equals(position);
    }

    public boolean isSameTeam(final Piece otherPiece) {
        return this.team == otherPiece.team;
    }

    public void move(Position position) {
        if (!getMovablePositions().contains(position)) {
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

    public Board getBoard() {
        return board;
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

}
