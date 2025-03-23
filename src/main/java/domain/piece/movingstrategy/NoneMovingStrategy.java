package domain.piece.movingstrategy;

import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;

public class NoneMovingStrategy implements JanggiPieceMovingStrategy {
    @Override
    public void checkPieceCanMove(JanggiSide mySide, JanggiPiece hurdlePiece, int hurdleCount,
                                  JanggiPiece targetPiece) {
        throw new IllegalStateException("움직일 기물이 존재하지 않습니다.");
    }
}
