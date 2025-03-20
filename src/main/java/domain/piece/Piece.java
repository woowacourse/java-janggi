package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.route.JanggiPieceRoute;
import java.util.List;

public abstract class Piece {
    protected int score;
    protected Side side;
    protected PieceStatus status;
    protected JanggiPieceRoute route;

    public Piece(int score, Side side, JanggiPieceRoute route) {
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

    public void captureIfNotMySide(Side otherSide) {
        if (otherSide == this.side) {
            return;
        }
        this.status = PieceStatus.CAPTURED;
    }

    public Side getSide() {
        return side;
    }

    public PieceStatus getStatus() {
        return status;
    }
}
