package domain.piece.movingstrategy;

import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;

public interface JanggiPieceMovingStrategy {

    void checkPieceCanMove(JanggiSide mySide, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece);
}
