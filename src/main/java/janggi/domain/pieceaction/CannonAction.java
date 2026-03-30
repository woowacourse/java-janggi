package janggi.domain.pieceaction;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.Rule;
import janggi.domain.movement.RuleOfCannon;
import java.util.Collection;
import java.util.List;

public class CannonAction implements PieceAction {

    private static final List<Rule> RULES;

    static {
        RULES = List.of(
            new RuleOfCannon(Direction.SOUTH),
            new RuleOfCannon(Direction.NORTH),
            new RuleOfCannon(Direction.EAST),
            new RuleOfCannon(Direction.WEST));
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
