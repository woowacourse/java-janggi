package janggi.domain.pieceaction;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.OffLineMovement;
import janggi.domain.movement.LeapingRule;
import janggi.domain.movement.Rule;
import java.util.Collection;
import java.util.List;

public class HorseAction implements PieceAction {

    private static final List<Rule> RULES;

    static {
        RULES = List.of(
            new LeapingRule(List.of(
                new OffLineMovement(1, Direction.EAST),
                new OffLineMovement(1, Direction.NORTH_EAST))),
            new LeapingRule(List.of(
                new OffLineMovement(1, Direction.EAST),
                new OffLineMovement(1, Direction.SOUTH_EAST))),
            new LeapingRule(List.of(
                new OffLineMovement(1, Direction.NORTH),
                new OffLineMovement(1, Direction.NORTH_EAST))),
            new LeapingRule(List.of(
                new OffLineMovement(1, Direction.NORTH),
                new OffLineMovement(1, Direction.NORTH_WEST))),
            new LeapingRule(List.of(
                new OffLineMovement(1, Direction.WEST),
                new OffLineMovement(1, Direction.NORTH_WEST))),
            new LeapingRule(List.of(
                new OffLineMovement(1, Direction.WEST),
                new OffLineMovement(1, Direction.SOUTH_WEST))),
            new LeapingRule(List.of(
                new OffLineMovement(1, Direction.SOUTH),
                new OffLineMovement(1, Direction.SOUTH_WEST))),
            new LeapingRule(List.of(
                new OffLineMovement(1, Direction.SOUTH),
                new OffLineMovement(1, Direction.SOUTH_EAST))));
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
