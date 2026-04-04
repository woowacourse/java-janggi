package janggi.domain.movement;

import static janggi.domain.Position.MAXIMUM_ROW;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.List;
import java.util.Optional;

public class RuleOfCannon implements Rule {

    private final List<OnLineMovement> movementOrder;

    public RuleOfCannon(final Direction direction) {
        this.movementOrder = generateMovementOrder(direction);
    }

    private List<OnLineMovement> generateMovementOrder(final Direction direction) {
        return List.of(
            new OnLineMovement(MAXIMUM_ROW, direction),
            new OnLineMovement(MAXIMUM_ROW, direction));
    }

    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final OnLineMovement firstMovement = movementOrder.getFirst();
        final OnLineMovement secondMovement = movementOrder.getLast();
        final Piece piece = boardMediator.getPieceInPosition(from);
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
        if (!boardMediator.existsInPosition(position)) {
            return false;
        }
        final Piece target = boardMediator.getPieceInPosition(position);

        return !target.isSameTypeAs(me);
    }
}
