package janggi.domain.pieceaction;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.UnconstrainedMovement;
import janggi.domain.movement.Rule;
import janggi.domain.movement.RuleWithNoTraces;
import java.util.Collection;
import java.util.List;

public class ElephantAction implements PieceAction {

    private static final List<Rule> RULES;

    static {
        RULES = List.of(
            new RuleWithNoTraces(List.of(
                new UnconstrainedMovement(1, Direction.EAST),
                new UnconstrainedMovement(1, Direction.NORTH_EAST),
                new UnconstrainedMovement(1, Direction.NORTH_EAST))),
            new RuleWithNoTraces(List.of(
                new UnconstrainedMovement(1, Direction.EAST),
                new UnconstrainedMovement(1, Direction.SOUTH_EAST),
                new UnconstrainedMovement(1, Direction.SOUTH_EAST))),
            new RuleWithNoTraces(List.of(
                new UnconstrainedMovement(1, Direction.NORTH),
                new UnconstrainedMovement(1, Direction.NORTH_EAST),
                new UnconstrainedMovement(1, Direction.NORTH_EAST))),
            new RuleWithNoTraces(List.of(
                new UnconstrainedMovement(1, Direction.NORTH),
                new UnconstrainedMovement(1, Direction.NORTH_WEST),
                new UnconstrainedMovement(1, Direction.NORTH_WEST))),
            new RuleWithNoTraces(List.of(
                new UnconstrainedMovement(1, Direction.WEST),
                new UnconstrainedMovement(1, Direction.NORTH_WEST),
                new UnconstrainedMovement(1, Direction.NORTH_WEST))),
            new RuleWithNoTraces(List.of(
                new UnconstrainedMovement(1, Direction.WEST),
                new UnconstrainedMovement(1, Direction.SOUTH_WEST),
                new UnconstrainedMovement(1, Direction.SOUTH_WEST))),
            new RuleWithNoTraces(List.of(
                new UnconstrainedMovement(1, Direction.SOUTH),
                new UnconstrainedMovement(1, Direction.SOUTH_WEST),
                new UnconstrainedMovement(1, Direction.SOUTH_WEST))),
            new RuleWithNoTraces(List.of(
                new UnconstrainedMovement(1, Direction.SOUTH),
                new UnconstrainedMovement(1, Direction.SOUTH_EAST),
                new UnconstrainedMovement(1, Direction.SOUTH_EAST))));
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
