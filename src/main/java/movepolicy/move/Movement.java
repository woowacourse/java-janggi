package movepolicy.move;

import java.util.List;
import pieces.Side;
import position.Position;

public interface Movement {

    boolean canReach(final Position departure,final Position destination, final Side side);

    List<Position> findPathPositions(final Position departure, final Position destination, final Side side);
}
