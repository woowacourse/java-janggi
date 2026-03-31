package janggi.domain.movement;

import static janggi.domain.Position.MAXIMUM_ROW;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.List;

public class RuleOfCannon implements Rule {

    private final List<UnconstrainedMovement> movementOrder;

    public RuleOfCannon(final Direction direction) {
        this.movementOrder = generateMovementOrder(direction);
    }

    private List<UnconstrainedMovement> generateMovementOrder(final Direction direction) {
        return List.of(
            new UnconstrainedMovement(MAXIMUM_ROW, direction),
            new UnconstrainedMovement(MAXIMUM_ROW, direction));
    }

    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final UnconstrainedMovement firstMovement = movementOrder.getFirst();
        final UnconstrainedMovement secondMovement = movementOrder.getLast();
        final Piece piece = boardMediator.getPieceInPosition(from);

        from = firstMovement.calculateBlockedPosition(from, boardMediator);
        if (!canJump(piece, from, boardMediator)) {
            return List.of();
        }
        return secondMovement.calculateTraces(from, piece, boardMediator)
            .stream()
            .distinct().toList();
    }

    private boolean canJump(final Piece me, final Position position,
        final BoardMediator boardMediator) {
        if (!boardMediator.existsInPosition(position)) {
            return false;
        }
        final Piece target = boardMediator.getPieceInPosition(position);

        return !target.isSameTypeAs(me);
    }
}
