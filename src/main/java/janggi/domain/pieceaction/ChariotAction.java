package janggi.domain.pieceaction;

import static janggi.domain.Position.MAXIMUM_COLUMN;
import static janggi.domain.Position.MAXIMUM_ROW;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.OnLineMovement;
import janggi.domain.movement.OnLineRule;
import janggi.domain.movement.Rule;
import java.util.Collection;
import java.util.List;

public class ChariotAction implements PieceAction {

    private static final List<Rule> RULES;

    static {
        RULES = List.of(
            OnLineRule.of(new OnLineMovement(MAXIMUM_ROW, Direction.SOUTH)),
            OnLineRule.of(new OnLineMovement(2, Direction.SOUTH_EAST)),
            OnLineRule.of(new OnLineMovement(MAXIMUM_COLUMN, Direction.EAST)),
            OnLineRule.of(new OnLineMovement(2, Direction.NORTH_EAST)),
            OnLineRule.of(new OnLineMovement(MAXIMUM_ROW, Direction.NORTH)),
            OnLineRule.of(new OnLineMovement(2, Direction.NORTH_WEST)),
            OnLineRule.of(new OnLineMovement(MAXIMUM_COLUMN, Direction.WEST)),
            OnLineRule.of(new OnLineMovement(2, Direction.SOUTH_WEST)));
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
