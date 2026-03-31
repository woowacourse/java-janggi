package janggi.domain.pieceaction;

import static janggi.domain.Position.MAXIMUM_COLUMN;
import static janggi.domain.Position.MAXIMUM_ROW;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.ConstrainedMovement;
import janggi.domain.movement.Direction;
import janggi.domain.movement.UnconstrainedMovement;
import janggi.domain.movement.Rule;
import janggi.domain.movement.RuleWithTraces;
import java.util.Collection;
import java.util.List;

public class ChariotAction implements PieceAction {

    private static final List<Rule> RULES;

    static {
        RULES = List.of(
            RuleWithTraces.of(new ConstrainedMovement(MAXIMUM_ROW, Direction.SOUTH)),
            RuleWithTraces.of(new ConstrainedMovement(MAXIMUM_ROW, Direction.NORTH)),
            RuleWithTraces.of(new ConstrainedMovement(MAXIMUM_COLUMN, Direction.EAST)),
            RuleWithTraces.of(new ConstrainedMovement(MAXIMUM_COLUMN, Direction.WEST)));
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
