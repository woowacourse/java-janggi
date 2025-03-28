package janggi.piece;

import janggi.board.JanggiScore;
import janggi.board.VisibleBoard;
import janggi.coordinate.Position;

public class Soldier extends Piece {

    private static final int SOLDIER_DISTANCE = 1;
    private static final double SOLDIER_PALACE_DISTANCE = Math.sqrt(2); 
    private static final JanggiScore KILL_JANGGI_SCORE = new JanggiScore(2);

    public Soldier(final Country country) {
        super(country);
    }

    @Override
    public JanggiScore plusScore(final JanggiScore janggiScore) {
        return KILL_JANGGI_SCORE.plus(janggiScore);
    }

    @Override
    protected boolean canMove(final Position now, final Position destination, final VisibleBoard visibleBoard) {
        if (country == Country.HAN && !now.isXLessThan(destination)) {
            return false;
        }
        if(country == Country.CHO && !now.isXGreaterThan(destination)){
            return false;
        }

        if (isInsidePalaceMove(now, destination)) {
            return now.calculateDistance(destination) == SOLDIER_PALACE_DISTANCE;
        }

        return now.calculateDistance(destination) == SOLDIER_DISTANCE;
    }

    private boolean isInsidePalaceMove(final Position now, final Position destination) {
        final Country opponentCountry = country.toggleCountry();
        if((now.isCornerInPalace(opponentCountry) && destination.isCenterInPalace(opponentCountry))
        || (now.isCenterInPalace(opponentCountry) && destination.isCornerInPalace(opponentCountry))){
            return true;
        }
        return false;
    }

}
