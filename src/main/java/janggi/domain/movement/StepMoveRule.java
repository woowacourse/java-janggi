package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public class StepMoveRule implements MoveRule {

    private final List<Movement> movementOrder;

    public StepMoveRule(final List<Movement> movementOrder) {
        this.movementOrder = movementOrder;
    }

    public static StepMoveRule elephantShape(Direction straight, Direction diagonal) {
        return new StepMoveRule(List.of(
                new Movement(straight),
                new Movement(diagonal),
                new Movement(diagonal)
        ));
    }

    public static StepMoveRule horseShape(Direction straight, Direction diagonal) {
        return new StepMoveRule(List.of(
                new Movement(straight),
                new Movement(diagonal)
        ));
    }

    @Override
    public List<Position> execute(Position from, final TeamType teamType, final BoardMediator boardMediator) {
        for (int index = 0; index < movementOrder.size() - 1; index++) {
            final Movement movement = movementOrder.get(index);
            if (!movement.canMove(from) || movement.isBlocked(from, boardMediator)) {
                return List.of();
            }
            from = movement.calculateDestination(from, teamType, boardMediator);
        }
        return findLastPosition(from, teamType, boardMediator);
    }

    private List<Position> findLastPosition(final Position from, TeamType teamType, final BoardMediator boardMediator) {
        final Movement lastMovement = movementOrder.getLast();
        if (!lastMovement.canMove(from) || !lastMovement.hasReachablePosition(from, teamType, boardMediator)) {
            return List.of();
        }
        final Position destination = lastMovement.calculateNextPosition(from);
        return List.of(destination);
    }
}
