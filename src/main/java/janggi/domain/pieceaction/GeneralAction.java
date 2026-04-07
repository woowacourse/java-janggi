package janggi.domain.pieceaction;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.Palace;
import janggi.domain.movement.Direction;
import janggi.domain.movement.OnLineMovement;
import janggi.domain.movement.LinearRule;
import janggi.domain.movement.Rule;
import java.util.Collection;
import java.util.List;

public class GeneralAction implements PieceAction {

    private static final List<Rule> RULES;

    static {
        RULES = List.of(
            LinearRule.of(new OnLineMovement(1, Direction.NORTH_WEST)),
            LinearRule.of(new OnLineMovement(1, Direction.NORTH)),
            LinearRule.of(new OnLineMovement(1, Direction.NORTH_EAST)),
            LinearRule.of(new OnLineMovement(1, Direction.WEST)),
            LinearRule.of(new OnLineMovement(1, Direction.EAST)),
            LinearRule.of(new OnLineMovement(1, Direction.SOUTH_WEST)),
            LinearRule.of(new OnLineMovement(1, Direction.SOUTH)),
            LinearRule.of(new OnLineMovement(1, Direction.SOUTH_EAST)));
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
