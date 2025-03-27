package domain.piece.state;

import domain.JanggiPosition;
import domain.piece.Side;

public interface PalaceMovable {
    boolean isInPalace(Side side, JanggiPosition afterPosition);

    boolean passesThroughCenter(Side side, JanggiPosition beforePosition, JanggiPosition afterPosition);
}
