package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.game.Score;
import java.util.Map;

public class Soldier extends Piece {

    private static final int SOLDIER_SCORE = 2;

    public Soldier(final Side side) {
        super(side, new Score(SOLDIER_SCORE));
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Map<Position, Piece> board) {
        if (start.isMoveDiagonalInPalace(end)) {
            return start.isOneStep(end) && isValidDirection(start, end);
        }

        return isValidMovingRule(start, end) && isValidDirection(start, end);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    private boolean isValidMovingRule(final Position start, final Position end) {
        int absDeltaX = start.absDeltaX(end);
        int absDeltaY = start.absDeltaY(end);
        return (absDeltaX == 1 && absDeltaY == 0) || (absDeltaX == 0 && absDeltaY == 1);
    }

    private boolean isValidDirection(final Position start, final Position end) {
        return (side == Side.RED && isValidRedSideDirection(start, end))
                || (side == Side.BLUE && isValidBlueSideDirection(start, end));
    }

    private boolean isValidRedSideDirection(final Position start, final Position end) {
        return start.isDown(end) || start.isHorizontalMove(end);
    }

    private boolean isValidBlueSideDirection(final Position start, final Position end) {
        return start.isUp(end) || start.isHorizontalMove(end);
    }
}
