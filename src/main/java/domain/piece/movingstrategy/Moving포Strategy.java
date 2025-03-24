package domain.piece.movingstrategy;

import domain.piece.JanggiPiece;
import domain.piece.JanggiPieceType;
import domain.piece.JanggiSide;

public class Moving포Strategy implements JanggiPieceMovingStrategy {

    @Override
    public void checkPieceCanMove(JanggiSide mySide, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        if (hurdleCount != 1) {
            throw new IllegalStateException("포는 장애물 1개를 뛰어넘어야 합니다.");
        }
        if (hurdlePiece.isTypeOf(JanggiPieceType.포)) {
            throw new IllegalStateException("포는 포를 넘을 수 없습니다.");
        }
        if (targetPiece.isTeamOf(mySide)) {
            throw new IllegalStateException("같은 팀의 기물은 잡을 수 없습니다.");
        }
        if (targetPiece.isTypeOf(JanggiPieceType.포)) {
            throw new IllegalStateException("포는 포를 잡을 수 없습니다.");
        }
    }
}
