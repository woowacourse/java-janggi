package janggi.domain.pieceaction;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.Palace;
import janggi.domain.movement.Direction;
import janggi.domain.movement.SlidingMovement;
import janggi.domain.movement.SlidingRule;
import janggi.domain.movement.Rule;
import java.util.Collection;
import java.util.List;

public class GuardAction implements PieceAction {

    private static final List<Rule> RULES;

    static {
        RULES = List.of(
            SlidingRule.of(new SlidingMovement(1, Direction.NORTH_WEST)),
            SlidingRule.of(new SlidingMovement(1, Direction.NORTH)),
            SlidingRule.of(new SlidingMovement(1, Direction.NORTH_EAST)),
            SlidingRule.of(new SlidingMovement(1, Direction.WEST)),
            SlidingRule.of(new SlidingMovement(1, Direction.EAST)),
            SlidingRule.of(new SlidingMovement(1, Direction.SOUTH_WEST)),
            SlidingRule.of(new SlidingMovement(1, Direction.SOUTH)),
            SlidingRule.of(new SlidingMovement(1, Direction.SOUTH_EAST)));
    }

    @Override
    public List<Position> calculateMovablePositions(final Position from,
        final BoardMediator boardMediator) {
        return RULES.stream()
            .map(rule -> rule.execute(from, boardMediator))
            .flatMap(Collection::stream)
            .filter(Palace::hasPosition)
            .toList();
    }
}
