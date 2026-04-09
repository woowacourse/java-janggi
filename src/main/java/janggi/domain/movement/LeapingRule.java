package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import janggi.utils.Lists;
import java.util.List;
import java.util.Optional;

public class LeapingRule implements Rule {

    private final List<LeapingMovement> movementOrder;

    public LeapingRule(final List<LeapingMovement> movementOrder) {
        this.movementOrder = movementOrder;
    }

    public static LeapingRule of(final LeapingMovement movement) {
        return new LeapingRule(List.of(movement));
    }


    @Override
    public List<Position> execute(final Position from, final BoardMediator boardMediator) {
        final Piece piece = boardMediator.getPieceByPosition(from);
        final Optional<Position> penultimatePosition = moveUpToLast(from, boardMediator);
        return penultimatePosition.map(
            position -> proceedFinalMovement(position, piece, boardMediator)).orElseGet(List::of);
    }

    private Optional<Position> moveUpToLast(final Position from, final BoardMediator boardMediator) {
        final List<LeapingMovement> movementsExceptLast = Lists.exceptLast(movementOrder);
        Position updatedPosition = from;
        for (final LeapingMovement movement : movementsExceptLast) {
            if (!movement.canMove(updatedPosition) || movement.isBlocked(updatedPosition, boardMediator)) {
                return Optional.empty();
            }
            updatedPosition = movement.calculateDestination(updatedPosition, boardMediator);
        }
        return Optional.of(updatedPosition);
    }

    private List<Position> proceedFinalMovement(final Position from, final Piece piece,
        final BoardMediator boardMediator) {
        final LeapingMovement lastMovement = movementOrder.getLast();
        if (lastMovement.canMove(from) && lastMovement.canCatchAnyOnPath(piece, from,
            boardMediator)) {
            return List.of(lastMovement.calculateBlockedPosition(from, boardMediator));
        }
        return List.of();
    }
}
