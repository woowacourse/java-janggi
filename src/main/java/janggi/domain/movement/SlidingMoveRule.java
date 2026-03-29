package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.List;

public class SlidingMoveRule implements MoveRule {

    private final List<Movement> movementOrder;

    public SlidingMoveRule(final List<Movement> movementOrder) {
        this.movementOrder = movementOrder;
    }

    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final Piece piece = boardMediator.getPieceInPosition(from);
        final Movement movement = movementOrder.getFirst();
        return movement.calculateTraces(from, piece, boardMediator);
    }
}
