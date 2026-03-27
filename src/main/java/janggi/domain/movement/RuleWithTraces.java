package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class RuleWithTraces implements Rule {

    private final List<Movement> movementOrder;

    public RuleWithTraces(final List<Movement> movementOrder) {
        this.movementOrder = movementOrder;
    }

    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final List<Position> traces = new ArrayList<>();
        final List<Movement> movementOrderWithoutLast = getMovementOrderWithoutLast();
        final Movement lastMovement = movementOrder.getLast();
        final Piece piece = boardMediator.getPieceInPosition(from);
        for (final Movement movement : movementOrderWithoutLast) {
            traces.addAll(movement.calculateTraces(from, piece, boardMediator));
            traces.add(movement.calculateDestination(from, boardMediator));
            from = traces.getLast();
        }
        traces.addAll(lastMovement.calculateTraces(from, piece, boardMediator));
        return traces;
    }

    private List<Movement> getMovementOrderWithoutLast() {
        final List<Movement> movementOrderWithoutLast = new ArrayList<>(movementOrder);
        movementOrderWithoutLast.removeLast();
        return movementOrderWithoutLast;
    }
}
