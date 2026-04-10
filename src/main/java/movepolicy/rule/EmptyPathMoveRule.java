package movepolicy.rule;

public class EmptyPathMoveRule implements MoveRule {

    private final MoveRule moveRule;

    private EmptyPathMoveRule(final MoveRule moveRule) {
        this.moveRule = moveRule;
    }

    public static EmptyPathMoveRule withOtherSideTargetRule() {
        return new EmptyPathMoveRule(new OtherSideTargetMoveRule());
    }

    @Override
    public void validate(final MoveTrace moveTrace) {
        moveRule.validate(moveTrace);
        validatePathIsEmpty(moveTrace);
    }

    private void validatePathIsEmpty(final MoveTrace moveTrace) {
        if (moveTrace.hasPathPieces()) {
            throw new IllegalArgumentException("이동 경로엔 기물이 있을 수 없습니다.");
        }
    }
}
