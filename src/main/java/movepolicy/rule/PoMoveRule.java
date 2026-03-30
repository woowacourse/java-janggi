package movepolicy.rule;

import java.util.List;
import pieces.Piece;

public class PoMoveRule implements MoveRule {

    private static final int PATH_PIECES_COUNT = 1;

    @Override
    public void validate(MoveTrace moveTrace) {
        validateMovingPieceIsPo(moveTrace);
        validatePathPieces(moveTrace);
        validateTargetIsAttackable(moveTrace);
    }

    private void validateMovingPieceIsPo(MoveTrace moveTrace) {
        if (!moveTrace.isMovingPieceIsPo()) {
            throw new IllegalArgumentException("해당 규칙은 포에만 적용할 수 있습니다.");
        }
    }

    private void validatePathPieces(MoveTrace moveTrace) {
        List<Piece> pathPieces = moveTrace.getPathPieces();
        if (pathPieces.size() != PATH_PIECES_COUNT) {
            throw new IllegalArgumentException("이동 경로엔 기물이 1개 존재해야 합니다.");
        }
        if (pathPieces.getFirst().isPo()) {
            throw new IllegalArgumentException("포는 포를 뛰어 넘을 수 없습니다.");
        }
    }

    private void validateTargetIsAttackable(MoveTrace moveTrace) {
        if (moveTrace.isTargetEmpty()) {
            return;
        }
        if (moveTrace.isTargetSameSide()) {
            throw new IllegalArgumentException("같은 진영의 말은 공격할 수 없습니다.");
        }
        if (moveTrace.isTargetPiecePo()) {
            throw new IllegalArgumentException("포는 포를 공격할 수 없습니다.");
        }
    }
}
