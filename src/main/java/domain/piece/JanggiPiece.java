package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.route.JanggiPieceRoute;
import java.util.List;

public abstract class JanggiPiece {
    protected int score;
    protected JanggiSide side;
    protected PieceStatus status;
    protected JanggiPieceRoute route;

    public JanggiPiece(int score, JanggiSide side, JanggiPieceRoute route) {
        this.score = score;
        this.side = side;
        this.status = PieceStatus.ACTIVE;
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
        this.status = PieceStatus.CAPTURED;
    }

    public JanggiSide getSide() {
        return side;
    }

    public PieceStatus getStatus() {
        return status;
    }
}
