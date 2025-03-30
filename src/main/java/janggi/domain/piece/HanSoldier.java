package janggi.domain.piece;

import janggi.domain.board.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.piece.move.MoveStrategy;
import janggi.domain.piece.move.Path;
import janggi.domain.piece.move.strategy.NoObstacleStrategy;
import java.util.List;
import java.util.Set;

public class HanSoldier extends Soldier {

    private static final Set<List<Direction>> PATHS = Set.of(
            List.of(Direction.DOWN),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT)
    );

    private static final MoveStrategy movestrategy = new NoObstacleStrategy(
            ((start, end) -> Path.calculatePath(start, end, PATHS)));

    public HanSoldier(Dynasty dynasty) {
        super(PieceType.HAN_SOLIDER, dynasty, movestrategy);
    }
}
