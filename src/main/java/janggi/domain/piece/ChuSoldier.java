package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.piece.moveStrategy.FixedRangeMoveStrategy;
import janggi.domain.piece.moveStrategy.MoveStrategy;
import java.util.List;
import java.util.Set;

public class ChuSoldier extends Soldier {

    private static final Set<List<Direction>> PATHS = Set.of(
            List.of(Direction.UP),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT)
    );

    private static final MoveStrategy MOVE_STRATEGY = new FixedRangeMoveStrategy();

    public ChuSoldier() {
        super(PATHS, MOVE_STRATEGY);
    }
}
