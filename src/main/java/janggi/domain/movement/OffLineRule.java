package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import janggi.utils.Lists;
import java.util.List;

public class OffLineRule implements Rule {

    private final List<OffLineMovement> movementOrder;

    public OffLineRule(final List<OffLineMovement> movementOrder) {
        this.movementOrder = movementOrder;
    }

    public static OffLineRule of(final OffLineMovement movement) {
        return new OffLineRule(List.of(movement));
    }


    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final Piece piece = boardMediator.getPieceInPosition(from);
        final List<OffLineMovement> movementOrderExceptLast = Lists.exceptLast(movementOrder);
        final OffLineMovement lastMovement = movementOrder.getLast();
        for (final OffLineMovement movement : movementOrderExceptLast) {
            if (!movement.canMove(from) || movement.isBlocked(from, boardMediator)) {
                return List.of();
            }
            from = movement.calculateDestination(from, boardMediator);
        }
        if (lastMovement.canMove(from) && lastMovement.canCatchAnyOnPath(piece, from,
            boardMediator)) {
            return List.of(lastMovement.calculateBlockedPosition(from, boardMediator));
        }
        return List.of();
    }
}
