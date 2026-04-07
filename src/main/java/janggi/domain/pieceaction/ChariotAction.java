package janggi.domain.pieceaction;

import static janggi.domain.Position.MAXIMUM_COLUMN;
import static janggi.domain.Position.MAXIMUM_ROW;
import static janggi.domain.board.Palace.PALACE_SIDE_LENGTH;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.OnLineMovement;
import janggi.domain.movement.LinearRule;
import janggi.domain.movement.Rule;
import java.util.Collection;
import java.util.List;

public class ChariotAction implements PieceAction {

    private static final List<Rule> RULES;

    static {
        RULES = List.of(
            LinearRule.of(new OnLineMovement(MAXIMUM_ROW, Direction.SOUTH)),
            LinearRule.of(new OnLineMovement(PALACE_SIDE_LENGTH, Direction.SOUTH_EAST)),
            LinearRule.of(new OnLineMovement(MAXIMUM_COLUMN, Direction.EAST)),
            LinearRule.of(new OnLineMovement(PALACE_SIDE_LENGTH, Direction.NORTH_EAST)),
            LinearRule.of(new OnLineMovement(MAXIMUM_ROW, Direction.NORTH)),
            LinearRule.of(new OnLineMovement(PALACE_SIDE_LENGTH, Direction.NORTH_WEST)),
            LinearRule.of(new OnLineMovement(MAXIMUM_COLUMN, Direction.WEST)),
            LinearRule.of(new OnLineMovement(PALACE_SIDE_LENGTH, Direction.SOUTH_WEST)));
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
