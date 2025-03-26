package janggi.piece;

import janggi.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Piece {

    private final PieceProfile pieceProfile;
    protected Position position;

    protected Piece(final PieceProfile pieceProfile, final Position position) {
        this.pieceProfile = pieceProfile;
        this.position = position;
    }

    public void moveTo(final Position futurePosition, final Map<Position, Piece> janggiBoard) {
        isMove(futurePosition);
        validateTeam(janggiBoard.get(futurePosition));
        checkObstacle(futurePosition, janggiBoard);
    }

    public void updatePiecePositionBy(final Position position) {
        this.position = position;
    }

    protected abstract void checkObstacle(final Position futurePosition, Map<Position, Piece> janggiBoard);

    public abstract List<Position> makeRoute(final Position position);

    protected abstract boolean isMove(final Position position);

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
        return other != null && isSame(other.getPieceProfile().getNation());
    }

    private boolean isNotSameTeam(final Team currentTurnTeam) {
        return !isSame(currentTurnTeam);
    }

    private boolean isSame(final Team other) {
        return pieceProfile.getNation().isSameNation(other);
    }

    public boolean isChoNation() {
        return pieceProfile.isCho();
    }

    public boolean isHanNation() {
        return pieceProfile.isHan();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Piece piece = (Piece) o;
        return Objects.equals(getPieceProfile(), piece.getPieceProfile()) && Objects.equals(
                getPosition(), piece.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPieceProfile(), getPosition());
    }

    public Position getBoardPosition() {
        return position;
    }

    public PieceProfile getPieceProfile() {
        return pieceProfile;
    }

    public Position getPosition() {
        return position;
    }

    public String getType() {
        return pieceProfile.getPieceType().getValue();
    }

    protected abstract boolean isPo();
}
