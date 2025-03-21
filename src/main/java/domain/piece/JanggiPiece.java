package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import java.util.List;

public abstract class JanggiPiece {

    protected final JanggiSide side;
    protected final JanggiPieceType type;
    protected JanggiPieceStatus status;

    public JanggiPiece(final JanggiSide side, final JanggiPieceType type) {
        this.side = side;
        this.status = JanggiPieceStatus.ACTIVE;
        this.type = type;
    }

    public List<Pattern> findPath(final JanggiPosition origin, final JanggiPosition destination) {
        return type.getRoute(origin, destination);
    }

    public boolean isEmpty() {
        return false;
    }

    public void checkPieceCanMove(JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        if (isMyTeam(targetPiece)) {
            throw new IllegalStateException("같은 팀의 기물은 잡을 수 없습니다.");
        }
        if (hurdleCount != 0) {
            throw new IllegalStateException("해당 기물은 장애물을 뛰어넘을 수 없습니다.");
        }
    }

    public void captureIfNotEmpty() {
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

    public boolean isCaptured() {
        return status == JanggiPieceStatus.CAPTURED;
    }
}
