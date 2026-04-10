package movepolicy.rule;

public class PoMoveRule implements MoveRule {

    private static final int PATH_PIECES_COUNT = 1;

    private final MoveRule origin;

    private PoMoveRule(final MoveRule moveRule) {
        this.origin = moveRule;
    }

    public static PoMoveRule withOtherSideTargetRule() {
        return new PoMoveRule(new OtherSideTargetMoveRule());
    }

    @Override
    public void validate(final MoveTrace moveTrace) {
        validateMovingPieceIsPo(moveTrace);
        validatePathPiecesCount(moveTrace);
        validatePieceBetweenIsNotPo(moveTrace);
        origin.validate(moveTrace);
        validateTargetIsNotPo(moveTrace);
    }

    private void validateMovingPieceIsPo(final MoveTrace moveTrace) {
        if (!moveTrace.isMovingPiecePo()) {
            throw new IllegalArgumentException("해당 규칙은 포에만 적용할 수 있습니다.");
        }
    }

    private void validatePathPiecesCount(final MoveTrace moveTrace) {
        if (!moveTrace.hasPathPieceCount(PATH_PIECES_COUNT)) {
            throw new IllegalArgumentException("이동 경로엔 기물이 1개 존재해야 합니다.");
        }
    }

    private void validatePieceBetweenIsNotPo(final MoveTrace moveTrace) {
        if (moveTrace.hasPoInPath()) {
            throw new IllegalArgumentException("포는 포를 뛰어 넘을 수 없습니다.");
        }
    }

    private void validateTargetIsNotPo(final MoveTrace moveTrace) {
        if (moveTrace.isTargetPiecePo()) {
            throw new IllegalArgumentException("포는 포를 공격할 수 없습니다.");
        }
    }
}
