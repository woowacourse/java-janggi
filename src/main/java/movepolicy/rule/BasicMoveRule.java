package movepolicy.rule;

public class BasicMoveRule implements MoveRule {

    @Override
    public void validate(MoveTrace moveTrace) {
        validatePathIsEmpty(moveTrace);
        validateTargetIsAttackable(moveTrace);
    }

    private static void validatePathIsEmpty(MoveTrace moveTrace) {
        if (moveTrace.hasPathPiece()) {
            throw new IllegalArgumentException("이동 경로엔 기물이 있을 수 없습니다.");
        }
    }

    private void validateTargetIsAttackable(MoveTrace moveTrace) {
        if (moveTrace.isTargetEmpty()) {
            return;
        }
        if (moveTrace.isTargetSameSide()) {
            throw new IllegalArgumentException("같은 진영의 말은 공격할 수 없습니다.");
        }
    }
}
