package fixture;

import domain.board.MoveContext;
import domain.board.Palace;
import domain.coordination.Coordination;

public class MoveContextFactory {

    private static final Palace PALACE = new Palace();

    public static MoveContext create(Coordination from, Coordination to) {
        return PALACE.createMoveContext(from, to);
    }
}
