package participant;

import pieces.Side;

public interface Turn {

    Turn move();

    Side side();
}
