package domain.piece.state;

import domain.piece.Side;

public interface PalaceMovable {
    boolean isInPalace(Side side, int afterFile, int afterRank);

    boolean passesThroughCenter(Side side, int beforeFile, int beforeRank, int afterFile, int afterRank);
}
