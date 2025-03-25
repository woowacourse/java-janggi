package domain.piece.movementrule;

import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;
import janggiexception.PieceNotExistException;

public class NoneMovementStrategy implements JanggiPieceMovementRule {
    @Override
    public void checkPieceCanMove(JanggiSide mySide, JanggiPiece hurdlePiece, int hurdleCount,
                                  JanggiPiece targetPiece) {
        throw new PieceNotExistException();
    }
}
