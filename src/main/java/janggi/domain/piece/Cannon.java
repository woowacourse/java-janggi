package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.piece.moveStrategy.CannonMoveStrategy;
import janggi.domain.piece.moveStrategy.MoveStrategy;
import java.util.List;
import java.util.Set;

public class Cannon extends PieceAbstractInterface {

    private static final Set<List<Direction>> PATHS = Set.of(
            List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    private static final MoveStrategy MOVE_STRATEGY = new CannonMoveStrategy();

    public Cannon() {
        super(PATHS, MOVE_STRATEGY);
    }
}