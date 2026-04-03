package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.List;

public class StepMoveRule implements MoveRule {

    private final List<Movement> movementOrder;

    public StepMoveRule(final List<Movement> movementOrder) {
        this.movementOrder = movementOrder;
    }

    public static StepMoveRule elephantShape(Direction straight, Direction diagonal) {
        return new StepMoveRule(List.of(
                new Movement(1, straight),
                new Movement(1, diagonal),
                new Movement(1, diagonal)
        ));
    }

    public static StepMoveRule horseShape(Direction straight, Direction diagonal) {
        return new StepMoveRule(List.of(
                new Movement(1, straight),
                new Movement(1, diagonal)
        ));
    }

    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final Piece piece = boardMediator.getPieceInPosition(from);
        for (int index = 0; index < movementOrder.size() - 1; index++) {
            final Movement movement = movementOrder.get(index);
            if (!movement.canMove(from) || movement.isBlocked(from, boardMediator)) {
                return List.of();
            }
            from = movement.calculateDestination(from, piece, boardMediator);
        }
        return findLastPosition(from, piece, boardMediator);
    }

    private List<Position> findLastPosition(final Position from, final Piece piece, final BoardMediator boardMediator) {
        final Movement lastMovement = movementOrder.getLast();
        if (!lastMovement.canMove(from) || !lastMovement.hasReachablePosition(piece, from, boardMediator)) {
            return List.of();
        }
        final Position destination = lastMovement.findFirstOccupiedPositionOrMax(from, piece, boardMediator);
        return List.of(destination);
    }
}
