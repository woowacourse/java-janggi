package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import janggi.utils.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RuleWithTraces implements Rule {

    private final List<ConstrainedMovement> movementOrder;

    public RuleWithTraces(final List<ConstrainedMovement> movementOrder) {
        this.movementOrder = movementOrder;
    }

    public static RuleWithTraces of(final ConstrainedMovement movement) {
        return new RuleWithTraces(List.of(movement));
    }

    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final List<Position> traces = new ArrayList<>();
        final List<ConstrainedMovement> movementOrderExceptLast = Lists.exceptLast(movementOrder);
        final Movement lastMovement = movementOrder.getLast();
        final Piece piece = boardMediator.getPieceInPosition(from);
        for (final ConstrainedMovement movement : movementOrderExceptLast) {
            traces.addAll(movement.calculateTraces(from, piece, boardMediator));
            final Optional<Position> destination = movement.calculateDestination(from,
                boardMediator);
            destination.ifPresent(traces::add);
            from = traces.getLast();
        }
        traces.addAll(lastMovement.calculateTraces(from, piece, boardMediator));
        return traces;
    }
}
