package movepolicy.rule;

public class OtherSideTargetMoveRule implements MoveRule {

    @Override
    public void validate(final MoveTrace moveTrace) {
        if (moveTrace.isTargetEmpty()) {
            return;
        }
        if (moveTrace.isTargetSameSide()) {
            throw new IllegalArgumentException("같은 진영의 말은 공격할 수 없습니다.");
        }
    }
}
