package janggi.piece.rule.movement;

import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.piece.rule.block.RequiredBlockCountRule;

public abstract class MovementRule {

    protected static String EXCEPTION_MESSAGE = "이동할 수 없는 지점입니다.";

    private final RequiredBlockCountRule requiredBlockCountRule;

    protected MovementRule(final RequiredBlockCountRule requiredBlockCountRule) {
        this.requiredBlockCountRule = requiredBlockCountRule;
    }

    public void validate(final Board board, final Position departure, final Position destination) {
        validateMovement(board, departure, destination);
        validateBlockCount(board, departure, destination);
    }

    protected abstract void validateMovement(final Board board, final Position departure, final Position destination);

    private void validateBlockCount(final Board board, final Position departure, final Position destination) {
        requiredBlockCountRule.validate(board, departure, destination);
    }
}
