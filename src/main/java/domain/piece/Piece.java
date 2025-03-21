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

    public abstract boolean isObstacleCountAllowed(final int obstacleCount);

    public Team getTeam() {
        return team;
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
}
