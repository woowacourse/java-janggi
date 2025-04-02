package domain.piece.movementrule;

import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;
import janggiexception.BlockedByFriendlyPieceException;
import janggiexception.HurdleExistException;

public class GeneralMovementRule implements JanggiPieceMovementRule {

    private static final GeneralMovementRule INSTANCE = new GeneralMovementRule();

    private GeneralMovementRule() {
    }

    public static GeneralMovementRule getInstance() {
        return INSTANCE;
    }

    @Override
    public void checkPieceCanMove(JanggiSide mySide, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        if (targetPiece.isTeamOf(mySide)) {
            throw new BlockedByFriendlyPieceException();
        }
        if (hurdleCount != 0) {
            throw new HurdleExistException();
        }
    }
}
