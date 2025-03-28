package domain.piece;

import domain.piece.route.Route;
import domain.position.JanggiPosition;

public class JanggiPiece {

    private final JanggiSide side;
    private final JanggiPieceType type;
    private boolean isCaptured;

    public JanggiPiece(final JanggiSide side, final JanggiPieceType type) {
        this.side = side;
        this.isCaptured = false;
        this.type = type;
    }

    public boolean isEmpty() {
        return type == JanggiPieceType.EMPTY;
    }

    public void validateCanMove(JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        type.validateCanMove(this.side, hurdlePiece, hurdleCount, targetPiece);
    }

    public Route getRoute(JanggiPosition origin, JanggiPosition destination) {
        return type.getRoute(side, origin, destination);
    }

    public boolean isTypeOf(JanggiPieceType expectedType) {
        return type == expectedType;
    }

    public boolean isTeamOf(JanggiSide expectedTeam) {
        return side == expectedTeam;
    }

    public void capture() {
        this.isCaptured = true;
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

    public int getScore() {
        return type.getScore();
    }
}
