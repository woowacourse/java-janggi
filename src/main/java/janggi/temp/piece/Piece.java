package janggi.temp.piece;

import janggi.temp.Board;
import janggi.temp.Team;
import janggi.temp.position.Position;
import java.util.Objects;

public abstract class Piece {

    private final Team team;

    public Piece(final Team team) {
        this.team = team;
    }

    public abstract void validateMove(final Position source, final Position destination,
                                      final Board board);

    public abstract Type type();

    public Team team() {
        return team;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Piece piece = (Piece) o;
        return team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(team);
    }
}
