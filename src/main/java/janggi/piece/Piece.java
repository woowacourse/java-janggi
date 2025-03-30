package janggi.piece;

import janggi.board.palace.Palace;
import janggi.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Piece {

    private final PieceType pieceType;
    private final Team team;

    protected Piece(final PieceType pieceType, final Team team) {
        this.pieceType = pieceType;
        this.team = team;
    }

    public abstract void moveTo(final Position currentPosition, final Position targetPosition,
                                final Map<Position, Piece> janggiBoard, final Palace palace);

    public abstract List<Position> makeRoute(final Position currentPosition, final Position targetPosition);

    public double sumScore(final double totalScore) {
        final Score score = this.getPieceType().getScore();
        return score.sum(totalScore);
    }

    protected void checkObstacle(final Position currentPosition, final Position targetPosition,
                                 final Map<Position, Piece> janggiBoard) {
    }

    public void validateTeam(final Team currentTurnTeam) {
        if (isNotSameTeam(currentTurnTeam)) {
            throw new IllegalArgumentException("[ERROR] 다른 팀의 기물을 선택할 수 없습니다.");
        }
    }

    protected void validateTeam(final Piece other) {
        if (isSameTeam(other)) {
            throw new IllegalArgumentException("[ERROR] 같은 팀의 기물을 잡을 수 없습니다.");
        }
    }

    private boolean isSameTeam(final Piece other) {
        return other != null && isSame(other.team);
    }

    private boolean isNotSameTeam(final Team currentTurnTeam) {
        return !isSame(currentTurnTeam);
    }

    private boolean isSame(final Team other) {
        return this.team.isSameTeam(other);
    }

    public boolean isChoNation() {
        return Team.isChu(this.team);
    }

    public boolean isHanNation() {
        return Team.isHan(this.team);
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
