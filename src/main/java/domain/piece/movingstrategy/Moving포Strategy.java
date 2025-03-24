package domain.piece.movingstrategy;

import domain.piece.JanggiPiece;
import domain.piece.JanggiPieceType;
import domain.piece.JanggiSide;
import janggiexception.BlockedByFriendlyPieceException;
import janggiexception.CannotCapture포Exception;
import janggiexception.CannotJump포Exception;
import janggiexception.NotExistOnlyOneHurdleException;

public class Moving포Strategy implements JanggiPieceMovingStrategy {

    @Override
    public void checkPieceCanMove(JanggiSide mySide, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        if (hurdleCount != 1) {
            throw new NotExistOnlyOneHurdleException();
        }
        if (hurdlePiece.isTypeOf(JanggiPieceType.포)) {
            throw new CannotJump포Exception();
        }
        if (targetPiece.isTeamOf(mySide)) {
            throw new BlockedByFriendlyPieceException();
        }
        if (targetPiece.isTypeOf(JanggiPieceType.포)) {
            throw new CannotCapture포Exception();
        }
    }
}
