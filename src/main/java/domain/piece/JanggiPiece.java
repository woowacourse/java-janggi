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

    public void captureIfNotMySide(final JanggiPiece other) {
        if (isMyTeam(other)) {
            return;
        }
        this.status = JanggiPieceStatus.CAPTURED;
    }

    public boolean isMyTeam(JanggiPiece other) {
        return this.side == other.side;
    }

    public JanggiPieceStatus getStatus() {
        return status;
    }
}
