package movepolicy.rule;

import java.util.List;
import pieces.FullPiece;

public class PoMoveRule implements MoveRule {

    private static final int INTERVERNING_PIECES_SIZE_THRESHOLD = 1;

    @Override
    public void validatePathPieces(MovePath movePath) {
        validateMovingPieceIsPo(movePath);

        List<FullPiece> pathFullPieces = movePath.getInterveningFullPieces();
        validateInterveningPieces(pathFullPieces);
        validateTarget(movePath);
    }

    private void validateMovingPieceIsPo(MovePath movePath) {
        if (!movePath.isMovingPieceIsPo()) {
            throw new IllegalArgumentException("해당 규칙은 포에만 적용할 수 있습니다.");
        }
    }

    private void validateInterveningPieces(List<FullPiece> interveningPieces) {
        if (interveningPieces.size() != INTERVERNING_PIECES_SIZE_THRESHOLD) {
            throw new IllegalArgumentException("이동 경로엔 기물이 1개 존재해야 합니다.");
        }
        if (interveningPieces.getFirst().isPo()) {
            throw new IllegalArgumentException("포는 포를 뛰어 넘을 수 없습니다.");
        }
    }

    private void validateTarget(MovePath movePath) {
        if (movePath.isTargetEmpty()) {
            return;
        }
        if (movePath.isTargetSameSide()) {
            throw new IllegalArgumentException("같은 진영의 말은 공격할 수 없습니다.");
        }
        if (movePath.isTargetPiecePo()) {
            throw new IllegalArgumentException("포는 포를 공격할 수 없습니다.");
        }
    }
}
