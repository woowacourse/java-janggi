package janggi.piece;

import janggi.board.JanggiScore;
import janggi.board.VisibleBoard;
import janggi.coordinate.Position;

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
    protected boolean canMove(final Position now, final Position destination, final VisibleBoard visibleBoard) {
        if (!destination.isInsidePalace()) {
            return false;
        }

        if (now.isCornerInPalace() && destination.isCenterInPalace()
                || now.isCenterInPalace() && destination.isCornerInPalace()) {
            return true;
        }

        return now.calculateDistance(destination) == GENERAL_DISTANCE;
    }

    @Override
    public boolean isGeneral() {
        return true;
    }

}
