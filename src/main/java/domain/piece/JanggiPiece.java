package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.route.JanggiPieceRoute;
import java.util.List;

public abstract class JanggiPiece {
    protected final int score;
    protected final JanggiSide side;
    protected JanggiPieceStatus status;
    protected JanggiPieceRoute route;

    public JanggiPiece(int score, final JanggiSide side, final JanggiPieceRoute route) {
        this.score = score;
        this.side = side;
        this.status = JanggiPieceStatus.ACTIVE;
        this.route = route;
    }

    public List<Pattern> findPath(final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        return route.getRoute(beforePosition, afterPosition);
    }

    public boolean isEmpty() {
        return false;
    }

    public void captureIfNotMyTeam(JanggiPiece hunterPiece) {
        if (isMyTeam(hunterPiece)) {
            throw new IllegalStateException("같은 팀의 기물은 잡을 수 없습니다.");
        }
        if (isEmpty()) {
            return;
        }

        this.status = JanggiPieceStatus.CAPTURED;
    }

    public boolean is포() {
        return false;
    }

    public boolean isMyTeam(JanggiPiece other) {
        return this.side == other.side;
    }

    public JanggiPieceStatus getStatus() {
        return status;
    }
}
