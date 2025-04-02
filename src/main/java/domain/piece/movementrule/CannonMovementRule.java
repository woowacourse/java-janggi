package domain.piece.movementrule;

import domain.piece.JanggiPiece;
import domain.piece.JanggiPieceType;
import domain.piece.JanggiSide;
import janggiexception.BlockedByFriendlyPieceException;
import janggiexception.CannotCaptureCannonException;
import janggiexception.CannotJumpCannonException;
import janggiexception.NotExistOnlyOneHurdleException;

public class CannonMovementRule implements JanggiPieceMovementRule {

    private static final CannonMovementRule INSTANCE = new CannonMovementRule();

    private CannonMovementRule() {
    }

    public static CannonMovementRule getInstance() {
        return INSTANCE;
    }

    @Override
    public void checkPieceCanMove(JanggiSide mySide, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        if (hurdleCount != 1) {
            throw new NotExistOnlyOneHurdleException();
        }
        if (hurdlePiece.isTypeOf(JanggiPieceType.CANNON)) {
            throw new CannotJumpCannonException();
        }
        if (targetPiece.isTeamOf(mySide)) {
            throw new BlockedByFriendlyPieceException();
        }
        if (targetPiece.isTypeOf(JanggiPieceType.CANNON)) {
            throw new CannotCaptureCannonException();
        }
    }
}
