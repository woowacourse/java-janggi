package janggi.piece;

import janggi.board.JanggiScore;
import janggi.board.VisibleBoard;
import janggi.coordinate.JanggiPosition;

public class General extends Piece {

    private static final int GENERAL_DISTANCE = 1;
    private static final JanggiScore KILL_JANGGI_SCORE = new JanggiScore(0);

    public General(final Country country) {
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

        return now.calculateDistance(destination) == GENERAL_DISTANCE;
    }

    private boolean isCenterToCorner(final JanggiPosition now, final JanggiPosition destination) {
        return now.isCenterInPalace() && destination.isCornerInPalace();
    }

    private boolean isCornerToCenter(final JanggiPosition now, final JanggiPosition destination) {
        return now.isCornerInPalace() && destination.isCenterInPalace();
    }

    @Override
    public boolean isGeneral() {
        return true;
    }

}
