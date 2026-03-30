package movepolicy.move;

import java.util.List;
import pieces.Side;
import position.Position;

public interface Movement {

    boolean canReach(Position departure, Position destination, Side side);

    List<Position> getInterveningPositions(Position departure, Position destination, Side side);
}
