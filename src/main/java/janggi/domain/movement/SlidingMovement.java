package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class SlidingMovement implements Movement {

    private final int maxDistance;
    private final Direction direction;

    public SlidingMovement(final int maxDistance, final Direction direction) {
        this.maxDistance = maxDistance;
        this.direction = direction;
    }

    @Override
    public boolean canMove(final Position from) {
        return from.checkNextBound(maxDistance, direction);
    }

    @Override
    public boolean canCatchAnyOnPath(final Piece me, final Position from,
        final BoardMediator boardMediator) {
        return IntStream.rangeClosed(1, maxDistance)
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .takeWhile(position -> boardMediator.canMove(position, direction.flip()))
            .anyMatch(to -> canCatchAt(me, to, boardMediator));
    }

    private boolean canCatchAt(final Piece me, final Position to,
        final BoardMediator boardMediator) {
        if (!boardMediator.existsByPosition(to)) {
            return true;
        }
        final Piece target = boardMediator.getPieceByPosition(to);
        return me.canCatch(target);
    }

    @Override
    public boolean isBlocked(final Position from, final BoardMediator boardMediator) {
        return IntStream.rangeClosed(1, maxDistance)
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .takeWhile(position -> boardMediator.canMove(position, direction.flip()))
            .anyMatch(boardMediator::existsByPosition);
    }

    public Optional<Position> calculateDestination(final Position from,
        final BoardMediator boardMediator) {

        return IntStream.rangeClosed(1, maxDistance).boxed().takeWhile(
                dist -> boardMediator.canMove(from.calculateNext(dist, direction), direction.flip()))
            .filter(dist -> boardMediator.existsByPosition(from.calculateNext(dist, direction)))
            .findFirst().map(dist -> decideFinalDestination(from, dist, boardMediator))
            .or(() -> fallbackPosition(from, direction, boardMediator));
    }

    private Position decideFinalDestination(final Position from, final int distance,
        final BoardMediator boardMediator) {
        final Piece me = boardMediator.getPieceByPosition(from);
        final Position to = from.calculateNext(distance, direction);
        final Piece toPiece = boardMediator.getPieceByPosition(to);
        if (me.isOnSameTeamAs(toPiece)) {
            return from.calculateNext(distance - 1, direction);
        }
        return from.calculateNext(distance, direction);
    }

    private Optional<Position> fallbackPosition(final Position from, final Direction direction,
        final BoardMediator boardMediator) {
        final int movableDistance = IntStream.rangeClosed(1, maxDistance).takeWhile(
            distance -> boardMediator.canMove(from.calculateNext(distance, direction),
                direction.flip())).reduce((first, second) -> second).orElse(0);
        if (movableDistance == 0) {
            return Optional.empty();
        }
        return Optional.of(from.calculateNext(movableDistance, direction));
    }

    public Optional<Position> calculateBlockedPosition(final Position from,
        final BoardMediator boardMediator) {
        return IntStream.rangeClosed(1, maxDistance)
            .filter(distance -> from.checkNextBound(distance, direction))
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .takeWhile(position -> boardMediator.canMove(position, direction.flip()))
            .filter(boardMediator::existsByPosition).findFirst();
    }

    @Override
    public List<Position> calculatePath(final Position from, final Piece me,
        final BoardMediator boardMediator) {
        if (!boardMediator.canMove(from, direction)) {
            return List.of();
        }

        List<Position> path = new ArrayList<>(IntStream.rangeClosed(1, maxDistance)
            .filter(distance -> from.checkNextBound(distance, direction))
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .takeWhile(position -> !boardMediator.existsByPosition(position)
                && boardMediator.canMove(position, direction.flip())).toList());
        calculateBlockedPosition(from, boardMediator)
            .filter(position -> boardMediator.canMove(position, direction.flip())
                && canMoveToBlockedPosition(me, position, boardMediator))
            .ifPresent(path::add);
        return path;
    }

    private boolean canMoveToBlockedPosition(final Piece me, final Position blockedPosition,
        final BoardMediator boardMediator) {
        return boardMediator.existsByPosition(blockedPosition) && me.canCatch(
            boardMediator.getPieceByPosition(blockedPosition));
    }

}
