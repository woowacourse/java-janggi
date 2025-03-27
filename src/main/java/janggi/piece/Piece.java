package janggi.piece;

import janggi.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Piece {

    private final PieceProfile pieceProfile;

    protected Piece(final PieceProfile pieceProfile) {
        this.pieceProfile = pieceProfile;
    }

    public void moveTo(final Position presentPosition, final Position futurePosition,
                       final Map<Position, Piece> janggiBoard) {
        canMoveBy(presentPosition, futurePosition);
        validateTeam(janggiBoard.get(futurePosition));
        checkObstacle(presentPosition, futurePosition, janggiBoard);
    }

    protected void checkObstacle(final Position presentPosition, final Position futurePosition,
                                 final Map<Position, Piece> janggiBoard) {
    }

    public abstract List<Position> makeRoute(final Position presentPosition, final Position position);

    protected abstract void canMoveBy(final Position preesntPosition, final Position position);

    protected void validateTeam(final Piece other) {
        if (isSameTeam(other)) {
            throw new IllegalArgumentException("[ERROR] 같은 팀의 기물을 잡을 수 없습니다.");
        }
    }

    public void validateTeam(final Team currentTurnTeam) {
        if (isNotSameTeam(currentTurnTeam)) {
            throw new IllegalArgumentException("[ERROR] 다른 팀의 기물을 선택할 수 없습니다.");
        }
    }

    private boolean isSameTeam(final Piece other) {
        return other != null && isSame(other.getPieceProfile().getTeam());
    }

    private boolean isNotSameTeam(final Team currentTurnTeam) {
        return !isSame(currentTurnTeam);
    }

    private boolean isSame(final Team other) {
        return pieceProfile.getTeam().isSameTeam(other);
    }

    public boolean isChoNation() {
        return pieceProfile.isCho();
    }

    public boolean isHanNation() {
        return pieceProfile.isHan();
    }

    public PieceProfile getPieceProfile() {
        return pieceProfile;
    }

    public String getType() {
        return pieceProfile.getPieceType().getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Piece piece = (Piece) o;
        return Objects.equals(getPieceProfile(), piece.getPieceProfile());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPieceProfile());
    }
}
