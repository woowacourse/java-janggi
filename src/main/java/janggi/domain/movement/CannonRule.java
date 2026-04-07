package janggi.domain.movement;

import static janggi.domain.Position.MAXIMUM_ROW;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.List;
import java.util.Optional;

public class CannonRule implements Rule {

    private final List<SlidingMovement> movementOrder;

    public CannonRule(final Direction direction) {
        this.movementOrder = generateMovementOrder(direction);
    }

    private List<SlidingMovement> generateMovementOrder(final Direction direction) {
        return List.of(
            new SlidingMovement(MAXIMUM_ROW, direction),
            new SlidingMovement(MAXIMUM_ROW, direction));
    }

    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final SlidingMovement firstMovement = movementOrder.getFirst();
        final SlidingMovement secondMovement = movementOrder.getLast();
        final Piece piece = boardMediator.getPieceByPosition(from);
        final Optional<Position> blockedPosition = firstMovement.calculateBlockedPosition(from,
            boardMediator);

        if (blockedPosition.isEmpty() || !canJump(piece, blockedPosition.get(), boardMediator)) {
            return List.of();
        }
        return secondMovement.calculatePath(blockedPosition.get(), piece, boardMediator)
            .stream()
            .distinct().toList();
    }

    private boolean canJump(final Piece me, final Position position,
        final BoardMediator boardMediator) {
        if (!boardMediator.existsByPosition(position)) {
            return false;
        }
        final Piece target = boardMediator.getPieceByPosition(position);

        return !target.isSameTypeAs(me);
    }
}
