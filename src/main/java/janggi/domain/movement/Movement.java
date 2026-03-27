package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class Movement {

    private final int maxDistance;
    private final Direction direction;

    public Movement(int maxDistance, Direction direction) {
        this.maxDistance = maxDistance;
        this.direction = direction;
    }

    public boolean canKill(final Piece me, final Position from, final BoardMediator boardMediator) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            Position to = from.calculateNext(distance, direction);
            if (!boardMediator.existsInPosition(to)) {
                return true;
            }
            Piece target = boardMediator.getPieceInPosition(to);
            if (me.canKill(target)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBlocked(final Position from, final BoardMediator boardMediator) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            Position to = from.calculateNext(distance, direction);
            if (boardMediator.existsInPosition(to)) {
                return false;
            }
        }
        return true;
    }

    public Position calculateDestination(final Position from, final Piece piece,
        final BoardMediator boardMediator) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            Position to = from.calculateNext(distance, direction);
            if (boardMediator.existsInPosition(to)) {
                Piece toPiece = boardMediator.getPieceInPosition(to);
                if (toPiece.belongsToTeam(piece.getTeamType())) {
                    return from.calculateNext(distance - 1, direction);
                }
                return from.calculateNext(distance, direction);
            }
        }
        return from.calculateNext(maxDistance, direction);
    }

    public Position calculateBlockedPosition(final Position from, final Piece piece,
        final BoardMediator boardMediator) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            Position to = from.calculateNext(distance, direction);
            if (boardMediator.existsInPosition(to)) {
                return from.calculateNext(distance, direction);
            }
        }
        return from.calculateNext(maxDistance, direction);
    }

    // 이동 가능한 경로의 자취 위치 리스트를 반환한다.
    public List<Position> calculateTraces(final Position from, final Piece piece,
        final BoardMediator boardMediator) {
        final List<Position> traces = new ArrayList<>();
        for (int distance = 1; distance <= maxDistance; distance++) {
            Position to = from.calculateNext(distance, direction);
            if (boardMediator.existsInPosition(to)) {
                Piece toPiece = boardMediator.getPieceInPosition(to);
                if (!toPiece.belongsToTeam(piece.getTeamType()) && piece.canKill(
                    toPiece)) {
                    traces.add(to);
                }
                return traces;
            }
            traces.add(to);
        }
        return traces;
    }
}
