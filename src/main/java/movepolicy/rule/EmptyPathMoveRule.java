package movepolicy.rule;

public class EmptyPathMoveRule implements MoveRule {

    private final MoveRule moveRule;

    public EmptyPathMoveRule(MoveRule moveRule) {
        this.moveRule = moveRule;
    }

    @Override
    public void validate(MoveTrace moveTrace) {
        moveRule.validate(moveTrace);
        validatePathIsEmpty(moveTrace);
    }

    private void validatePathIsEmpty(MoveTrace moveTrace) {
        if (moveTrace.hasPathPieces()) {
            throw new IllegalArgumentException("이동 경로엔 기물이 있을 수 없습니다.");
        }
    }
}
