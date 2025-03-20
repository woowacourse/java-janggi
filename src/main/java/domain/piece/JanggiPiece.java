package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.route.JanggiPieceRoute;
import java.util.List;

public abstract class JanggiPiece {
    protected int score;
    protected JanggiSide side;
    protected JanggiPieceStatus status;
    protected JanggiPieceRoute route;

    public JanggiPiece(int score, JanggiSide side, JanggiPieceRoute route) {
        this.score = score;
        this.side = side;
        this.status = JanggiPieceStatus.ACTIVE;
        this.route = route;
    }

    public List<Pattern> findPath(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        return route.getRoute(beforePosition, afterPosition);
    }

    public boolean isEmpty() {
        return false;
    }

    public void captureIfNotMySide(JanggiSide otherSide) {
        if (otherSide == this.side) {
            return;
        }
        this.status = JanggiPieceStatus.CAPTURED;
    }

    public JanggiSide getSide() {
        return side;
    }

    public JanggiPieceStatus getStatus() {
        return status;
    }
}
