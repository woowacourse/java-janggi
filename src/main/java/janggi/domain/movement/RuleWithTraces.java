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
        final Piece piece = boardMediator.getPieceInPosition(from);
        for (int index = 0; index < movementOrder.size() - 1; index++) {
            final Movement movement = movementOrder.get(index);
            traces.addAll(movement.calculateTraces(from, piece, boardMediator));
            final Position destination = movement.calculateDestination(from, piece, boardMediator);
            traces.add(destination);
            from = destination;
        }
        return findLastPosition(from, piece, boardMediator, traces);
    }

    private List<Position> findLastPosition(final Position from, final Piece piece, final BoardMediator boardMediator,
                                            final List<Position> traces) {
        final Movement lastMovement = movementOrder.getLast();
        traces.addAll(lastMovement.calculateTraces(from, piece, boardMediator));
        return traces;
    }
}
