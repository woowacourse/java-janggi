package domain.piece.state;

import domain.JanggiPosition;
import domain.piece.Side;

public class Palace implements PalaceMovable {
    @Override
    public boolean isInPalace(Side side, int afterFile, int afterRank) {
        boolean isInPalace = false;
        if (side.equals(Side.CHO)) {
            isInPalace = ((afterFile == 0 || afterFile == 9 || afterFile == 8) && afterRank >= 4 && afterRank <= 6);
        }
        if (side.equals(Side.HAN)) {
            isInPalace = (afterFile >= 1 && afterFile <= 3 && afterRank >= 4 && afterRank <= 6);
        }
        return isInPalace;
    }

    @Override
    public boolean passesThroughCenter(Side side, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        boolean isDiagonal = beforePosition.isDiagonalTo(afterPosition);

        if (!isDiagonal) {
            return true;
        }

        return beforePosition.isPassThroughCenter(side, afterPosition);
    }
}



