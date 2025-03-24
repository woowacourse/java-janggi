package domain.piece.movingstrategy;

import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;
import janggiexception.PieceNotExistException;

public class NoneMovingStrategy implements JanggiPieceMovingStrategy {
    @Override
    public void checkPieceCanMove(JanggiSide mySide, JanggiPiece hurdlePiece, int hurdleCount,
                                  JanggiPiece targetPiece) {
        throw new PieceNotExistException();
    }
}
