package janggi.domain.pieceaction;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.Rule;
import janggi.domain.movement.CannonRule;
import java.util.Collection;
import java.util.List;

public class CannonAction implements PieceAction {

    private static final List<Rule> RULES;

    static {
        RULES = List.of(
            new CannonRule(Direction.SOUTH),
            new CannonRule(Direction.SOUTH_EAST),
            new CannonRule(Direction.EAST),
            new CannonRule(Direction.NORTH_EAST),
            new CannonRule(Direction.NORTH),
            new CannonRule(Direction.NORTH_WEST),
            new CannonRule(Direction.WEST),
            new CannonRule(Direction.SOUTH_WEST));
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
