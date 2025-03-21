package domain.piece;

import domain.Team;
import domain.board.BoardPosition;
import domain.board.Offset;
import java.util.List;
import java.util.Objects;

public class Piece {

    protected final PieceType pieceType;
    protected final Team team;

    public Piece(
            final PieceType pieceType,
            final Team team
    ) {
        this.pieceType = pieceType;
        this.team = team;
    }

    public List<Offset> findMovementRule(
            final BoardPosition before,
            final BoardPosition after
    ) {
        final Offset offset = after.calculateOffset(before);
        validateNotMove(offset);
        return pieceType.findMovementRule(offset, team)
                .orElseThrow(() -> new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다."));
    }

    protected void validateNotMove(final Offset offset) {
        if (offset.hasNoMovement()) {
            throw new IllegalArgumentException("기물을 같은 위치로 이동시킬 수 없습니다.");
        }
    }

    public boolean isObstacleCountAllowed(final int obstacleCount) {
        return pieceType.getAllowObstacleCount() == obstacleCount;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }

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
