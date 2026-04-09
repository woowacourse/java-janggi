package janggi.domain.pieceaction;

import static janggi.domain.Position.MAXIMUM_COLUMN;
import static janggi.domain.Position.MAXIMUM_ROW;
import static janggi.domain.board.Palace.PALACE_SIDE_LENGTH;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.SlidingMovement;
import janggi.domain.movement.SlidingRule;
import janggi.domain.movement.Rule;
import java.util.Collection;
import java.util.List;

public class ChariotAction implements PieceAction {

    private static final List<Rule> RULES;

    static {
        RULES = List.of(
            SlidingRule.of(new SlidingMovement(MAXIMUM_ROW, Direction.SOUTH)),
            SlidingRule.of(new SlidingMovement(PALACE_SIDE_LENGTH, Direction.SOUTH_EAST)),
            SlidingRule.of(new SlidingMovement(MAXIMUM_COLUMN, Direction.EAST)),
            SlidingRule.of(new SlidingMovement(PALACE_SIDE_LENGTH, Direction.NORTH_EAST)),
            SlidingRule.of(new SlidingMovement(MAXIMUM_ROW, Direction.NORTH)),
            SlidingRule.of(new SlidingMovement(PALACE_SIDE_LENGTH, Direction.NORTH_WEST)),
            SlidingRule.of(new SlidingMovement(MAXIMUM_COLUMN, Direction.WEST)),
            SlidingRule.of(new SlidingMovement(PALACE_SIDE_LENGTH, Direction.SOUTH_WEST)));
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
