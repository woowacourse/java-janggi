package janggi.domain.pieceaction;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.ConstrainedMovement;
import janggi.domain.movement.UnconstrainedMovement;
import janggi.domain.movement.Rule;
import janggi.domain.movement.RuleWithTraces;
import java.util.Collection;
import java.util.List;

public class GeneralAction implements PieceAction {

    private static final List<Rule> RULES;

    static {
        RULES = List.of(
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.NORTH_WEST)),
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.NORTH)),
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.NORTH_EAST)),
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.WEST)),
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.EAST)),
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.SOUTH_WEST)),
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.SOUTH)),
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.SOUTH_EAST)));
    }

    @Override
    public List<Position> calculateMovablePositions(final Position from,
        final BoardMediator boardMediator) {
        return RULES.stream()
            .map(rule -> rule.execute(from, boardMediator))
            .flatMap(Collection::stream)
            .toList();
    }
}
