package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import janggi.global.Pair;
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
    public List<Position> execute(final Position from, final BoardMediator boardMediator) {
        final List<Position> path = new ArrayList<>();
        final Piece piece = boardMediator.getPieceInPosition(from);
        final Pair<Position, List<Position>> moveResultUpToLast = moveUpToLast(from, piece, boardMediator);
        path.addAll(moveResultUpToLast.right());
        path.addAll(proceedFinalMovement(moveResultUpToLast.left(), piece, boardMediator));
        return path;
    }

    private Pair<Position, List<Position>> moveUpToLast(final Position from, final Piece piece, final BoardMediator boardMediator) {
        final List<Position> path = new ArrayList<>();
        final List<OnLineMovement> movementsExceptLast = Lists.exceptLast(movementOrder);
        Position updatedPosition = from;
        for (final OnLineMovement movement : movementsExceptLast) {
            path.addAll(movement.calculatePath(from, piece, boardMediator));
            final Optional<Position> destination = movement.calculateDestination(from,
                boardMediator);
            destination.ifPresent(path::add);
            updatedPosition = path.getLast();
        }
        return new Pair<>(updatedPosition, path);
    }

    private List<Position> proceedFinalMovement(final Position from, final Piece piece, final BoardMediator boardMediator) {
        final Movement lastMovement = movementOrder.getLast();
        return lastMovement.calculatePath(from, piece, boardMediator);
    }
}
