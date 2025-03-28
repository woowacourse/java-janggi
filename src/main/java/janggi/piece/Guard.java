package janggi.piece;

import janggi.board.JanggiScore;
import janggi.board.VisibleBoard;
import janggi.coordinate.JanggiPosition;

public class Guard extends Piece {

    private static final int GUARD_DISTANCE = 1;
    private static final JanggiScore KILL_JANGGI_SCORE = new JanggiScore(3);

    public Guard(final Country country) {
        super(country);
    }

    @Override
    public JanggiScore plusScore(final JanggiScore janggiScore) {
        return KILL_JANGGI_SCORE.plus(janggiScore);
    }

    @Override
    protected boolean canMove(final JanggiPosition now, final JanggiPosition destination, final VisibleBoard visibleBoard) {
        if (!destination.isInsidePalace(country)) {
            return false;
        }

        if (isCenterToCorner(now, destination) || isCornerToCenter(now, destination)) {
            return true;
        }

        return now.calculateDistance(destination) == GUARD_DISTANCE;
    }

    private boolean isCenterToCorner(final JanggiPosition now, final JanggiPosition destination) {
        return now.isCenterInPalace() && destination.isCornerInPalace();
    }

    private boolean isCornerToCenter(final JanggiPosition now, final JanggiPosition destination) {
        return now.isCornerInPalace() && destination.isCenterInPalace();
    }

}
