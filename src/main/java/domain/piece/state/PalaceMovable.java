package domain.piece.state;

import domain.JanggiPosition;
import domain.piece.Side;

public interface PalaceMovable {
    boolean isInPalace(Side side, int afterFile, int afterRank);

    boolean passesThroughCenter(Side side, JanggiPosition beforePosition, JanggiPosition afterPosition);
}
