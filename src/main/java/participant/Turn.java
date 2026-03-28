package participant;

import pieces.Piece;
import pieces.Side;

public interface Turn {

    Turn move();

    boolean isMatchSide(Side side);

    void validateSide(Piece piece);
}
