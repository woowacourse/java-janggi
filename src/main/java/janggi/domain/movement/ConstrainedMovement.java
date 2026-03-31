package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ConstrainedMovement implements Movement {

    private final int maxDistance;
    private final Direction direction;

    public ConstrainedMovement(final int maxDistance, final Direction direction) {
        this.maxDistance = maxDistance;
        this.direction = direction;
    }

    @Override
    public boolean canMove(final Position from) {
        return from.checkNextBound(maxDistance, direction);
    }

    @Override
    public boolean canCatch(final Piece me, final Position from,
        final BoardMediator boardMediator) {
        return IntStream.rangeClosed(1, maxDistance)
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .takeWhile(position -> boardMediator.canMove(position, direction.flip()))
            .anyMatch(to -> canCatchAt(me, to, boardMediator));
    }

    private boolean canCatchAt(final Piece me, final Position to,
        final BoardMediator boardMediator) {
        if (!boardMediator.existsInPosition(to)) {
            return true;
        }
        final Piece target = boardMediator.getPieceInPosition(to);
        return me.canCatch(target);
    }

    @Override
    public boolean isValid(final Position from, final BoardMediator boardMediator) {
        return IntStream.rangeClosed(1, maxDistance - 1)
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .allMatch(position -> boardMediator.canMove(position, direction));
    }

    @Override
    public boolean isBlocked(final Position from, final BoardMediator boardMediator) {
        return IntStream.rangeClosed(1, maxDistance)
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .takeWhile(position -> boardMediator.canMove(position, direction.flip()))
            .anyMatch(boardMediator::existsInPosition);
    }

    public Optional<Position> calculateDestination(final Position from,
        final BoardMediator boardMediator) {
        final boolean valid = isValid(from, boardMediator);

        return IntStream.rangeClosed(1, maxDistance)
            .boxed()
            .takeWhile(dist -> valid || boardMediator.canMove(from.calculateNext(dist, direction), direction.flip()))
            .filter(dist -> boardMediator.existsInPosition(from.calculateNext(dist, direction)))
            .findFirst()
            .map(dist -> decideFinalDestination(from, dist, boardMediator))
            .or(() -> fallbackPosition(from, direction, boardMediator));
    }

    private Position decideFinalDestination(final Position from, final int distance,
        final BoardMediator boardMediator) {
        final Piece me = boardMediator.getPieceInPosition(from);
        final Position to = from.calculateNext(distance, direction);
        final Piece toPiece = boardMediator.getPieceInPosition(to);
        if (me.isOnSameTeamAs(toPiece)) {
            return from.calculateNext(distance - 1, direction);
        }
        return from.calculateNext(distance, direction);
    }

    private Optional<Position> fallbackPosition(final Position from, final Direction direction, final BoardMediator boardMediator) {
        if (isValid(from, boardMediator)) {
            return Optional.of(from.calculateNext(maxDistance, direction));
        }
        final int movableDistance = IntStream.rangeClosed(1, maxDistance)
            .takeWhile(distance -> boardMediator.canMove(from.calculateNext(distance, direction),
                direction.flip()))
            .findFirst().orElse(0);
        if (movableDistance == 0) {
            return Optional.empty();
        }
        return Optional.of(from.calculateNext(movableDistance, direction));
    }

    public Optional<Position> calculateBlockedPosition(final Position from,
        final BoardMediator boardMediator) {
        return IntStream.rangeClosed(1, maxDistance)
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .takeWhile(position -> boardMediator.canMove(position, direction.flip()))
            .filter(boardMediator::existsInPosition)
            .findFirst();
    }

    @Override
    public List<Position> calculateTraces(final Position from, final Piece me,
        final BoardMediator boardMediator) {
        final List<Position> traces = new ArrayList<>(IntStream.rangeClosed(1, maxDistance)
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .takeWhile(
                position -> !boardMediator.existsInPosition(position) && boardMediator.canMove(
                    position, direction.flip()))
            .toList());
        final Position blockedPosition = from.calculateNext(traces.size() + 1, direction);

        if (boardMediator.existsInPosition(blockedPosition) &&
            me.canCatch(boardMediator.getPieceInPosition(blockedPosition))) {
            traces.add(blockedPosition);
        }
        return traces;
    }

}
