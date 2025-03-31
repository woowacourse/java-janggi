package domain.piece;

import domain.board.Movement;
import domain.board.Offset;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final Team team;

    protected Piece(
        final PieceType pieceType,
        final Team team
    ) {
        validateNotNull(pieceType, team);
        this.pieceType = pieceType;
        this.team = team;
    }

    private void validateNotNull(
        final PieceType pieceType,
        final Team team
    ) {
        if (pieceType == null || team == null) {
            throw new IllegalArgumentException("기물은 타입과 팀을 반드시 가져야 합니다.");
        }
    }

    public abstract List<Offset> findMovementRule(final Movement movement);

    public void validateMovementConditions(
        final List<Piece> obstacles,
        @Nullable
        final Piece destinationPiece
    ) {
        if (!obstacles.isEmpty()) {
            throw new IllegalArgumentException("해당 말은 장애물을 넘을 수 없습니다.");
        }
    }

    public boolean isSamePieceType(final PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public boolean isSameTeam(final Team other) {
        return team == other;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }

    public abstract Score getScore();

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Piece piece = (Piece) o;
        return pieceType == piece.pieceType && team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, team);
    }
}
