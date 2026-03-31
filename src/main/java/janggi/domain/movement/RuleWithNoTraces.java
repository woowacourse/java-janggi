package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import janggi.utils.Lists;
import java.util.List;

public class RuleWithNoTraces implements Rule {

    private final List<UnconstrainedMovement> movementOrder;

    public RuleWithNoTraces(final List<UnconstrainedMovement> movementOrder) {
        this.movementOrder = movementOrder;
    }

    public static RuleWithNoTraces of(final UnconstrainedMovement movement) {
        return new RuleWithNoTraces(List.of(movement));
    }


    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final Piece piece = boardMediator.getPieceInPosition(from);
        final List<UnconstrainedMovement> movementOrderExceptLast = Lists.exceptLast(movementOrder);
        final UnconstrainedMovement lastMovement = movementOrder.getLast();
        for (final UnconstrainedMovement movement : movementOrderExceptLast) {
            if (!movement.canMove(from) || movement.isBlocked(from, boardMediator)) {
                return List.of();
            }
            from = movement.calculateDestination(from, boardMediator);
        }
        if (lastMovement.canMove(from) && lastMovement.canCatch(piece, from, boardMediator)) {
            return List.of(lastMovement.calculateBlockedPosition(from, boardMediator));
        }
        return List.of();
    }
}
