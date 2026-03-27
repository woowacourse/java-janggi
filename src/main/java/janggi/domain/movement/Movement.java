package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Movement {

    private final int maxDistance;
    private final Direction direction;

    public Movement(int maxDistance, Direction direction) {
        this.maxDistance = maxDistance;
        this.direction = direction;
    }

    public boolean canMove(final Position from) {
        return from.checkNextBound(maxDistance, direction);
    }

    public boolean canCatch(final Piece me, final Position from,
        final BoardMediator boardMediator) {
        return IntStream.rangeClosed(1, maxDistance)
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .anyMatch(to -> canCatchAt(me, to, boardMediator));
    }

    private boolean canCatchAt(final Piece me, final Position to,
        final BoardMediator boardMediator) {
        if (!boardMediator.existsInPosition(to)) {
            return true;
        }
        Piece target = boardMediator.getPieceInPosition(to);
        return me.canKill(target);
    }

    public boolean isBlocked(final Position from, final BoardMediator boardMediator) {
        return IntStream.rangeClosed(1, maxDistance)
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .anyMatch(boardMediator::existsInPosition);
    }

    public Position calculateDestination(final Position from, final BoardMediator boardMediator) {
        return IntStream.rangeClosed(1, maxDistance)
            .boxed()
            .filter(distance ->
                boardMediator.existsInPosition(from.calculateNext(distance, direction)))
            .findFirst()
            .map(distance -> decideFinalDestination(from, distance, boardMediator))
            .orElse(from.calculateNext(maxDistance, direction));
    }

    private Position decideFinalDestination(final Position from, final int distance,
        final BoardMediator boardMediator) {
        final Piece me = boardMediator.getPieceInPosition(from);
        final Position to = from.calculateNext(distance, direction);
        final Piece toPiece = boardMediator.getPieceInPosition(to);
        if (toPiece.belongsToTeam(me.getTeamType())) {
            return from.calculateNext(distance - 1, direction);
        }
        return from.calculateNext(distance, direction);
    }

    public Position calculateBlockedPosition(final Position from,
        final BoardMediator boardMediator) {
        return IntStream.rangeClosed(1, maxDistance)
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .filter(boardMediator::existsInPosition)
            .findFirst().orElse(from.calculateNext(maxDistance, direction));
    }

    public List<Position> calculateTraces(final Position from, final Piece me,
        final BoardMediator boardMediator) {
        final List<Position> traces = new ArrayList<>(IntStream.rangeClosed(1, maxDistance)
            .mapToObj(distance -> from.calculateNext(distance, direction))
            .takeWhile(position -> !boardMediator.existsInPosition(position))
            .toList());
        final Position blockedPosition = from.calculateNext(traces.size() + 1, direction);
        if (boardMediator.existsInPosition(blockedPosition) &&
            me.canKill(boardMediator.getPieceInPosition(blockedPosition))) {
            traces.add(blockedPosition);
        }

        return traces;
    }
}
