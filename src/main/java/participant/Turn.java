package participant;

import pieces.Side;

public interface Turn {

    Turn move();

    boolean isMatchSide(Side side);
}
