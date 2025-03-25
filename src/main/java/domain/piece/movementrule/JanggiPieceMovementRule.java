package domain.piece.movementrule;

import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;

public interface JanggiPieceMovementRule {

    void checkPieceCanMove(JanggiSide mySide, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece);
}
