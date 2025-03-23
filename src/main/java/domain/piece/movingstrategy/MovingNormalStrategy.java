package domain.piece.movingstrategy;

import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;

public class MovingNormalStrategy implements JanggiPieceMovingStrategy {

    @Override
    public void checkPieceCanMove(JanggiSide mySide, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        if (targetPiece.isTeam(mySide)) {
            throw new IllegalStateException("같은 팀의 기물은 잡을 수 없습니다.");
        }
        if (hurdleCount != 0) {
            throw new IllegalStateException("해당 기물은 장애물을 뛰어넘을 수 없습니다.");
        }
    }
}
