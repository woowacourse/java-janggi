package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.game.Score;
import java.util.Map;

public class Guard extends Piece {

    private static final int GUARD_SCORE = 3;

    public Guard(final Side side) {
        super(side, new Score(GUARD_SCORE));
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Map<Position, Piece> board) {
        return isValidMovingRule(start, end) && start.isOneStep(end) && start.isMoveInPalace(end);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    private boolean isValidMovingRule(final Position start, final Position end) {
        return start.isVerticalMove(end) || start.isHorizontalMove(end) || start.isMoveDiagonalInPalace(end);
    }
}
