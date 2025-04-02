package domain.piece.movementrule;

import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;
import janggiexception.PieceNotExistException;

public class NoneMovementRule implements JanggiPieceMovementRule {

    private static final NoneMovementRule INSTANCE = new NoneMovementRule();

    private NoneMovementRule() {
    }

    public static NoneMovementRule getInstance() {
        return INSTANCE;
    }

    @Override
    public void checkPieceCanMove(JanggiSide mySide, JanggiPiece hurdlePiece, int hurdleCount,
                                  JanggiPiece targetPiece) {
        throw new PieceNotExistException();
    }
}
