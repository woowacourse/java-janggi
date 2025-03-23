package domain.piece;

import domain.position.JanggiPosition;
import domain.Pattern;
import java.util.List;

public class JanggiPiece {

    private final JanggiSide side;
    private final JanggiPieceType type;
    private boolean isCaptured;

    public JanggiPiece(final JanggiSide side, final JanggiPieceType type) {
        this.side = side;
        this.isCaptured = false;
        this.type = type;
    }

    public List<Pattern> getRoute(JanggiPosition origin, JanggiPosition destination) {
        return type.getRoute(origin, destination);
    }

    public boolean isEmpty() {
        return false;
    }

    public void checkPieceCanMove(JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        type.validateCanMove(this.side, hurdlePiece, hurdleCount, targetPiece);
    }

    public void captureIfNotEmpty() {
        if (isEmpty()) {
            return;
        }
        this.isCaptured = true;
    }

    public boolean isTypeOf(JanggiPieceType type) {
        return this.type == type;
    }

    public boolean isMyTeam(JanggiPiece other) {
        return this.side == other.side;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public JanggiPieceType getType() {
        return type;
    }

    public JanggiSide getSide() {
        return side;
    }

    public boolean isTeam(JanggiSide other) {
        return this.side == other;
    }
}
