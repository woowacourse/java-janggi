package domain.piece;

import domain.board.BoardPosition;
import domain.board.Offset;
import domain.board.movement.Movement;
import domain.janggi.Score;
import domain.janggi.Team;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final Team team;

    public Piece(final PieceType pieceType, final Team team) {
        this.pieceType = pieceType;
        this.team = team;
    }

    public List<Offset> findMovementRule(
            final BoardPosition before,
            final BoardPosition after
    ) {
        final Movement movement = Movement.of(before, after);
        validateMovement(movement);

        return createMovementRule(movement.calcaulteOffset());
    }

    protected abstract void validateMovement(final Movement movement);

    protected abstract List<Offset> createMovementRule(final Offset offset);

    public boolean isAllowedObstacles(final List<Piece> obstacles) {
        return obstacles.isEmpty();
    }

    public boolean isCatchable(final Piece piece) {
        return true;
    }

    public boolean isMyTeam(final Team team) {
        return this.team == team;
    }

    public boolean isGeneral() {
        return PieceType.GENERAL == pieceType;
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

    @Override
    public String toString() {
        return "(" + pieceType.getTitle() + ", " + team.getTitle() + ")";
    }

    public Score getScore() {
        return pieceType.getScore();
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
