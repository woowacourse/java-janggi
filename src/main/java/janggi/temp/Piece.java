package janggi.temp;

import java.util.Objects;
import java.util.Set;

public abstract class Piece {

    private final Position position;
    private final Team team;

    public Piece(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    public abstract Piece move(final Position destination, final Set<Piece> pieces);

    public Team team() {
        return team;
    }

    public Position position() {
        return position;
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
        return Objects.equals(position, piece.position) && team == piece.team;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(position);
        result = 31 * result + Objects.hashCode(team);
        return result;
    }
}
