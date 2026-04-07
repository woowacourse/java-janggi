package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import janggi.utils.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinearRule implements Rule {

    private final List<OnLineMovement> movementOrder;

    public LinearRule(final List<OnLineMovement> movementOrder) {
        this.movementOrder = movementOrder;
    }

    public static LinearRule of(final OnLineMovement movement) {
        return new LinearRule(List.of(movement));
    }

    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final List<Position> path = new ArrayList<>();
        final List<OnLineMovement> movementOrderExceptLast = Lists.exceptLast(movementOrder);
        final OnLineMovement lastMovement = movementOrder.getLast();
        final Piece piece = boardMediator.getPieceInPosition(from);
        for (final OnLineMovement movement : movementOrderExceptLast) {
            path.addAll(movement.calculatePath(from, piece, boardMediator));
            final Optional<Position> destination = movement.calculateDestination(from,
                boardMediator);
            destination.ifPresent(path::add);
            from = path.getLast();
        }
        path.addAll(lastMovement.calculatePath(from, piece, boardMediator));
        return path;
    }
}
