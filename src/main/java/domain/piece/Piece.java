package domain.piece;

import domain.Team;
import domain.board.BoardPosition;
import domain.board.Offset;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected final Team team;

    public Piece(final Team team) {
        this.team = team;
    }

    public abstract List<Offset> findMovementRule(
            final BoardPosition before,
            final BoardPosition after
    );

    public abstract boolean isAllowedObstacles(final List<Piece> obstacles);

    public abstract boolean isCatchable(final Piece piece);

    public boolean isMyTeam(final Team team) {
        return this.team == team;
    }

    @Override
    public boolean equals(final Object o) {
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

    public Team getTeam() {
        return team;
    }
}
