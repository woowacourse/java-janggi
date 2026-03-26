package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class Movement {
    private final BoardMediator boardMediator;
    private final int maxDistance;
    private final Direction direction;

    public Movement(int maxDistance, Direction direction, BoardMediator boardMediator) {
        this.maxDistance = maxDistance;
        this.direction = direction;
        this.boardMediator = boardMediator;
    }

    public boolean canReach(final Position from) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            Position to = from.calculateNext(distance, direction);
            if (boardMediator.existsInPosition(to)) {
                return false;
            }
        }
        return true;
    }

    public Position calculateDestination(final Position from) {
        Piece fromPiece = boardMediator.getPieceInPosition(from);
        for (int distance = 1; distance <= maxDistance; distance++) {
            Position to = from.calculateNext(distance, direction);
            if (boardMediator.existsInPosition(to)) {
                Piece toPiece = boardMediator.getPieceInPosition(to);
                if (toPiece.belongsToTeam(fromPiece.getTeamType())) {
                    return from.calculateNext(distance - 1, direction);
                }
                return from.calculateNext(distance, direction);
            }
        }
        return from.calculateNext(maxDistance, direction);
    }

    public List<Position> calculateTraces(final Position from) {
        final Piece fromPiece = boardMediator.getPieceInPosition(from);
        final List<Position> traces = new ArrayList<>();
        for (int distance = 1; distance <= maxDistance; distance++) {
            Position to = from.calculateNext(distance, direction);
            if (boardMediator.existsInPosition(to)) {
                Piece toPiece = boardMediator.getPieceInPosition(to);
                if (!toPiece.belongsToTeam(fromPiece.getTeamType())) {
                    traces.add(to);
                }
                return traces;
            }
            traces.add(to);
        }
        return traces;
    }
}
