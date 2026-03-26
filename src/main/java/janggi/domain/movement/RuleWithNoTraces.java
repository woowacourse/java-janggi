package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.List;

public class RuleWithNoTraces implements Rule {

    private final List<Movement> movementOrder;

    public RuleWithNoTraces(final List<Movement> movementOrder) {
        this.movementOrder = movementOrder;
    }

    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final Piece piece = boardMediator.getPieceInPosition(from);
        for (final Movement movement : movementOrder) {
            if (!movement.canReach(from, boardMediator)) {
                return List.of();
            }
            from = movement.calculateDestination(from, piece, boardMediator);
        }

        return List.of(from);
    }
}
