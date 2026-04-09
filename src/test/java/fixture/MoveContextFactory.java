package fixture;

import domain.move.MoveContext;
import domain.board.palace.Palace;
import domain.coordination.Coordination;

public class MoveContextFactory {

    private static final Palace PALACE = new Palace();

    public static MoveContext create(Coordination from, Coordination to) {
        return PALACE.createMoveContext(from, to);
    }
}
