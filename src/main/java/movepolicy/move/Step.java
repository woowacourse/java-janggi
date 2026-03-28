package movepolicy.move;

import pieces.Side;
import position.Position;

public interface Step {

    Position move(Position position, Side side);
}
