package janggi.piece;

import janggi.board.JanggiScore;
import janggi.board.VisibleBoard;
import janggi.coordinate.JanggiPosition;

public class Soldier extends Piece {

    private static final int SOLDIER_DISTANCE = 1;
    private static final JanggiScore KILL_JANGGI_SCORE = new JanggiScore(2);

    public Soldier(final Country country) {
        super(country);
    }

    @Override
    public JanggiScore plusScore(final JanggiScore janggiScore) {
        return KILL_JANGGI_SCORE.plus(janggiScore);
    }

    @Override
    protected boolean canMove(final JanggiPosition now, final JanggiPosition destination, final VisibleBoard visibleBoard) {
        if (!isDestinationDirectionFront(now, destination, country)) {
            return false;
        }

        if (!now.isSameLine(destination) && now.isSamePalace(destination)) {
            return isCornerToCenter(now, destination) || isCenterToCorner(now, destination);
        }

        return now.calculateDistance(destination) == SOLDIER_DISTANCE;
    }

    private boolean isDestinationDirectionFront(final JanggiPosition now, final JanggiPosition destination, final Country country) {
        if(country == Country.HAN){
            return now.isXLessThan(destination);
        }
        return now.isXGreaterThan(destination);
    }

    private boolean isCenterToCorner(final JanggiPosition now, final JanggiPosition destination) {
        return now.isCenterInPalace() && destination.isCornerInPalace();
    }

    private boolean isCornerToCenter(final JanggiPosition now, final JanggiPosition destination) {
        return now.isCornerInPalace() && destination.isCenterInPalace();
    }

}
